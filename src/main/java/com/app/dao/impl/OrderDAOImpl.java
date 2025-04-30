package com.app.dao.impl;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;
import com.app.common.enums.Roles;
import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.IOrderDAO;
import com.app.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.app.dao.impl.FoodItemDAOImpl.*;
import static com.app.dao.impl.ShoppingCartDAOImpl.QUANTITY;
import static com.app.dao.impl.UserDAOImpl.*;

public class OrderDAOImpl implements IOrderDAO {
    protected static final String ORDER_ID = "order_id";
    protected static final String DELIVERY_PERSON_ID = "delivery_person_id";
    protected static final String TOTAL_PRICE = "total_price";
    protected static final String ORDER_STATUS = "order_status";
    protected static final String ORDER_DATE_TIME = "order_date_time";
    protected static final String PAYMENT_STATUS = "payment_status";
    protected static final String FOOD_ITEMS_TOTAL = "food_items_total";
    private static final String ORDER_FOOD_ITEM_SQL = """
            select * from order_food_items ofi, food_item fi
            where order_id = ? and fi.food_item_id = ofi.food_item_id
            """;
    private static final String DELIVERY_PERSON_SQL = """
            select user_id, first_name, last_name from user_
            where user_id = ?
            """;

    @Override
    public int placeOrder(ShoppingCart shoppingCart, Order order) throws DBException {
        String orderSQL = """
                insert into order_(user_id, total_price, order_status, payment_status)
                select u.user_id, ?, ?, ?
                from user_ u where u.user_id = ?
                """;
        String orderFoodItemSQL = """
                insert into order_food_items(food_item_id, order_id, quantity, food_items_total)
                 select sc.food_item_id, o.order_id, sc.quantity,
                  round((fi.price-(fi.price*(fi.discount/100)))*sc.quantity, 2)
                 from shopping_cart sc, food_item fi, order_ o
                 where sc.user_id = o.user_id and sc.food_item_id = fi.food_item_id and order_id = ?;
                """;
        Connection connection = null;
        PreparedStatement psForOrder = null;
        PreparedStatement psForOrderFoodItems = null;
        ResultSet setOfGeneratedKeys = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            connection.setAutoCommit(false);
            psForOrder = connection.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS);
            psForOrder.setDouble(1, shoppingCart.getTotalPrice());
            psForOrder.setString(2, order.getOrderStatus().name());
            psForOrder.setString(3, order.getPaymentStatus().name());
            psForOrder.setInt(4, shoppingCart.getUserId());
            int i = psForOrder.executeUpdate();
            setOfGeneratedKeys = psForOrder.getGeneratedKeys();
            int generatedKey = 0;
            if (setOfGeneratedKeys.next()) {
                generatedKey = setOfGeneratedKeys.getInt(1);
            }
            psForOrderFoodItems = connection.prepareStatement(orderFoodItemSQL);
            psForOrderFoodItems.setInt(1, generatedKey);
            i += psForOrderFoodItems.executeUpdate();
            if (i > 1) {
                connection.commit();
            } else {
                connection.rollback();
            }
            return i;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new DBException(ex);
            }
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForOrder, setOfGeneratedKeys, connection);
            DBConnector.resourceCloser(psForOrderFoodItems, null, null);
        }
    }

    @Override
    public List<Order> getAllOrders() throws DBException {
        String orderSQL = """
                select u.user_id, u.first_name, u.last_name, o.order_id, o.delivery_person_id,
                o.order_status, o.order_date_time, o.total_price, o.payment_status
                from user_ u, order_ o where u.user_id = o.user_id
                """;
        Connection connection = null;
        PreparedStatement psForUserOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        PreparedStatement psForDeliveryPerson = null;
        ResultSet orderSet = null;
        ResultSet foodItemSet = null;
        ResultSet deliveryPersonSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            connection.setAutoCommit(false);
            psForUserOrder = connection.prepareStatement(orderSQL);
            orderSet = psForUserOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (orderSet.next()) {
                User user = new User();
                user.setUserId(orderSet.getInt(USER_ID));
                user.setFirstName(orderSet.getString(FIRST_NAME));
                user.setLastName(orderSet.getString(LAST_NAME));
                psForDeliveryPerson = connection.prepareStatement(DELIVERY_PERSON_SQL);
                psForDeliveryPerson.setInt(1, orderSet.getInt(DELIVERY_PERSON_ID));
                deliveryPersonSet = psForDeliveryPerson.executeQuery();
                User deliveryPerson = new User();
                if (deliveryPersonSet.next()) {
                    deliveryPerson.setUserId(orderSet.getInt(DELIVERY_PERSON_ID));
                    deliveryPerson.setFirstName(deliveryPersonSet.getString(FIRST_NAME));
                    deliveryPerson.setLastName(deliveryPersonSet.getString(LAST_NAME));
                }
                Order order = new Order();
                order.setOrderId(orderSet.getInt(ORDER_ID));
                order.setUser(user);
                order.setDeliveryPerson(deliveryPerson);
                order.setOrderStatus(OrderStatus.toEnum(orderSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(orderSet.getTimestamp(ORDER_DATE_TIME).toLocalDateTime());
                order.setTotalPrice(orderSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(orderSet.getString(PAYMENT_STATUS)));

                psForOrderFoodItem = connection.prepareStatement(ORDER_FOOD_ITEM_SQL);
                psForOrderFoodItem.setInt(1, orderSet.getInt(ORDER_ID));
                foodItemSet = psForOrderFoodItem.executeQuery();
                List<OrderFoodItems> orderFoodItemsList = new ArrayList<>();
                while (foodItemSet.next()) {
                    OrderFoodItems orderFoodItems = new OrderFoodItems();
                    FoodItem foodItem = new FoodItem();
                    foodItem.setFoodItemId(foodItemSet.getInt(FOOD_ITEM_ID));
                    foodItem.setFoodName(foodItemSet.getString(FOOD_NAME));
                    foodItem.setFoodDescription(foodItemSet.getString(FOOD_DESCRIPTION));
                    foodItem.setPrice(foodItemSet.getDouble(PRICE));
                    foodItem.setDiscount(foodItemSet.getDouble(DISCOUNT));
                    orderFoodItems.setFoodItem(foodItem);
                    orderFoodItems.setQuantity(foodItemSet.getInt(QUANTITY));
                    orderFoodItems.setFoodItemsTotal(Math.round(foodItemSet.getDouble(FOOD_ITEMS_TOTAL)));
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
                orderList.add(order);
            }
            connection.commit();
            return orderList;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new DBException(ex);
            }
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForUserOrder, orderSet, connection);
            DBConnector.resourceCloser(psForDeliveryPerson, deliveryPersonSet, null);
            DBConnector.resourceCloser(psForOrderFoodItem, foodItemSet, null);
        }
    }

    @Override
    public List<Order> getAllPreviousOrders(int userId, Roles roles) throws DBException {
        String users = "";
        if (roles == Roles.ROLE_DELIVERY_PERSON) {
            users = DELIVERY_PERSON_ID;
        }
        if (roles == Roles.ROLE_CUSTOMER) {
            users = USER_ID;
        }
        String userOrderSQL = String.format("""
                select u.user_id, u.first_name, u.last_name, o.order_id, o.delivery_person_id,
                o.order_status, o.order_date_time, o.total_price, o.payment_status
                from user_ u, order_ o where u.user_id = o.user_id and o.%s = ? and
                (order_status = 'DELIVERED' or order_status = 'CANCELLED')
                """, users);

        Connection connection = null;
        PreparedStatement psForUserOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        PreparedStatement psForDeliveryPerson = null;
        ResultSet orderSet = null;
        ResultSet foodItemSet = null;
        ResultSet deliveryPersonSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForUserOrder = connection.prepareStatement(userOrderSQL);
            psForUserOrder.setInt(1, userId);
            orderSet = psForUserOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (orderSet.next()) {
                User user = new User();
                user.setUserId(orderSet.getInt(USER_ID));
                user.setFirstName(orderSet.getString(FIRST_NAME));
                user.setLastName(orderSet.getString(LAST_NAME));
                psForDeliveryPerson = connection.prepareStatement(DELIVERY_PERSON_SQL);
                psForDeliveryPerson.setInt(1, orderSet.getInt(DELIVERY_PERSON_ID));
                deliveryPersonSet = psForDeliveryPerson.executeQuery();
                User deliveryPerson = new User();
                if (deliveryPersonSet.next()) {
                    deliveryPerson.setUserId(orderSet.getInt(DELIVERY_PERSON_ID));
                    deliveryPerson.setFirstName(deliveryPersonSet.getString(FIRST_NAME));
                    deliveryPerson.setLastName(deliveryPersonSet.getString(LAST_NAME));
                }
                Order order = new Order();
                order.setOrderId(orderSet.getInt(ORDER_ID));
                order.setUser(user);
                order.setDeliveryPerson(deliveryPerson);
                order.setOrderStatus(OrderStatus.toEnum(orderSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(orderSet.getTimestamp(ORDER_DATE_TIME).toLocalDateTime());
                order.setTotalPrice(orderSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(orderSet.getString(PAYMENT_STATUS)));

                psForOrderFoodItem = connection.prepareStatement(ORDER_FOOD_ITEM_SQL);
                psForOrderFoodItem.setInt(1, orderSet.getInt(ORDER_ID));
                foodItemSet = psForOrderFoodItem.executeQuery();
                List<OrderFoodItems> orderFoodItemsList = new ArrayList<>();
                while (foodItemSet.next()) {
                    OrderFoodItems orderFoodItems = new OrderFoodItems();
                    FoodItem foodItem = new FoodItem();
                    foodItem.setFoodItemId(foodItemSet.getInt(FOOD_ITEM_ID));
                    foodItem.setFoodName(foodItemSet.getString(FOOD_NAME));
                    foodItem.setFoodDescription(foodItemSet.getString(FOOD_DESCRIPTION));
                    foodItem.setPrice(foodItemSet.getDouble(PRICE));
                    foodItem.setDiscount(foodItemSet.getDouble(DISCOUNT));
                    orderFoodItems.setFoodItem(foodItem);
                    orderFoodItems.setQuantity(foodItemSet.getInt(QUANTITY));
                    orderFoodItems.setFoodItemsTotal(Math.round(foodItemSet.getDouble(FOOD_ITEMS_TOTAL)));
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForUserOrder, orderSet, connection);
            DBConnector.resourceCloser(psForDeliveryPerson, deliveryPersonSet, null);
            DBConnector.resourceCloser(psForOrderFoodItem, foodItemSet, null);
        }
    }

    @Override
    public List<Order> getAllOrders(OrderStatus orderStatus) throws DBException {
        String userOrderSQL = """
                select u.user_id, u.first_name, u.last_name, o.order_id, o.delivery_person_id,
                o.order_status, o.order_date_time, o.total_price, o.payment_status
                from user_ u, order_ o where u.user_id = o.user_id and order_status = ?
                """;

        Connection connection = null;
        PreparedStatement psForUserOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        PreparedStatement psForDeliveryPerson = null;
        ResultSet orderSet = null;
        ResultSet foodItemSet = null;
        ResultSet deliveryPersonSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForUserOrder = connection.prepareStatement(userOrderSQL);
            psForUserOrder.setString(1, orderStatus.name());
            orderSet = psForUserOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (orderSet.next()) {
                User user = new User();
                user.setUserId(orderSet.getInt(USER_ID));
                user.setFirstName(orderSet.getString(FIRST_NAME));
                user.setLastName(orderSet.getString(LAST_NAME));
                psForDeliveryPerson = connection.prepareStatement(DELIVERY_PERSON_SQL);
                psForDeliveryPerson.setInt(1, orderSet.getInt(DELIVERY_PERSON_ID));
                deliveryPersonSet = psForDeliveryPerson.executeQuery();
                User deliveryPerson = new User();
                if (deliveryPersonSet.next()) {
                    deliveryPerson.setUserId(orderSet.getInt(DELIVERY_PERSON_ID));
                    deliveryPerson.setFirstName(deliveryPersonSet.getString(FIRST_NAME));
                    deliveryPerson.setLastName(deliveryPersonSet.getString(LAST_NAME));
                }
                Order order = new Order();
                order.setOrderId(orderSet.getInt(ORDER_ID));
                order.setUser(user);
                order.setDeliveryPerson(deliveryPerson);
                order.setOrderStatus(OrderStatus.toEnum(orderSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(orderSet.getTimestamp(ORDER_DATE_TIME).toLocalDateTime());
                order.setTotalPrice(orderSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(orderSet.getString(PAYMENT_STATUS)));

                psForOrderFoodItem = connection.prepareStatement(ORDER_FOOD_ITEM_SQL);
                psForOrderFoodItem.setInt(1, orderSet.getInt(ORDER_ID));
                foodItemSet = psForOrderFoodItem.executeQuery();
                List<OrderFoodItems> orderFoodItemsList = new ArrayList<>();
                while (foodItemSet.next()) {
                    OrderFoodItems orderFoodItems = new OrderFoodItems();
                    FoodItem foodItem = new FoodItem();
                    foodItem.setFoodItemId(foodItemSet.getInt(FOOD_ITEM_ID));
                    foodItem.setFoodName(foodItemSet.getString(FOOD_NAME));
                    foodItem.setFoodDescription(foodItemSet.getString(FOOD_DESCRIPTION));
                    foodItem.setPrice(foodItemSet.getDouble(PRICE));
                    foodItem.setDiscount(foodItemSet.getDouble(DISCOUNT));
                    orderFoodItems.setFoodItem(foodItem);
                    orderFoodItems.setQuantity(foodItemSet.getInt(QUANTITY));
                    orderFoodItems.setFoodItemsTotal(Math.round(foodItemSet.getDouble(FOOD_ITEMS_TOTAL)));
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForUserOrder, orderSet, connection);
            DBConnector.resourceCloser(psForDeliveryPerson, deliveryPersonSet, null);
            DBConnector.resourceCloser(psForOrderFoodItem, foodItemSet, null);
        }
    }

    @Override
    public Order getOrder(int orderId) throws DBException {
        String userOrderSQL = """
                select u.user_id, u.first_name, u.last_name, o.order_id, o.delivery_person_id,
                o.order_status, o.order_date_time, o.total_price, o.payment_status
                from user_ u, order_ o where u.user_id = o.user_id and o.order_id = ?
                """;
        Connection connection = null;
        PreparedStatement psForUserOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        PreparedStatement psForDeliveryPerson = null;
        ResultSet orderSet = null;
        ResultSet foodItemSet = null;
        ResultSet deliveryPersonSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForUserOrder = connection.prepareStatement(userOrderSQL);
            psForUserOrder.setInt(1, orderId);
            orderSet = psForUserOrder.executeQuery();
            Order order = null;
            if (orderSet.next()) {
                User user = new User();
                user.setUserId(orderSet.getInt(USER_ID));
                user.setFirstName(orderSet.getString(FIRST_NAME));
                user.setLastName(orderSet.getString(LAST_NAME));
                psForDeliveryPerson = connection.prepareStatement(DELIVERY_PERSON_SQL);
                psForDeliveryPerson.setInt(1, orderSet.getInt(DELIVERY_PERSON_ID));
                deliveryPersonSet = psForDeliveryPerson.executeQuery();
                User deliveryPerson = new User();
                if (deliveryPersonSet.next()) {
                    deliveryPerson.setUserId(orderSet.getInt(DELIVERY_PERSON_ID));
                    deliveryPerson.setFirstName(deliveryPersonSet.getString(FIRST_NAME));
                    deliveryPerson.setLastName(deliveryPersonSet.getString(LAST_NAME));
                }
                order = new Order();
                order.setOrderId(orderSet.getInt(ORDER_ID));
                order.setUser(user);
                order.setDeliveryPerson(deliveryPerson);
                order.setOrderStatus(OrderStatus.toEnum(orderSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(orderSet.getTimestamp(ORDER_DATE_TIME).toLocalDateTime());
                order.setTotalPrice(orderSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(orderSet.getString(PAYMENT_STATUS)));

                psForOrderFoodItem = connection.prepareStatement(ORDER_FOOD_ITEM_SQL);
                psForOrderFoodItem.setInt(1, orderSet.getInt(ORDER_ID));
                foodItemSet = psForOrderFoodItem.executeQuery();
                List<OrderFoodItems> orderFoodItemsList = new ArrayList<>();
                while (foodItemSet.next()) {
                    OrderFoodItems orderFoodItems = new OrderFoodItems();
                    FoodItem foodItem = new FoodItem();
                    foodItem.setFoodItemId(foodItemSet.getInt(FOOD_ITEM_ID));
                    foodItem.setFoodName(foodItemSet.getString(FOOD_NAME));
                    foodItem.setFoodDescription(foodItemSet.getString(FOOD_DESCRIPTION));
                    foodItem.setPrice(foodItemSet.getDouble(PRICE));
                    foodItem.setDiscount(foodItemSet.getDouble(DISCOUNT));
                    orderFoodItems.setFoodItem(foodItem);
                    orderFoodItems.setQuantity(foodItemSet.getInt(QUANTITY));
                    orderFoodItems.setFoodItemsTotal(Math.round(foodItemSet.getDouble(FOOD_ITEMS_TOTAL)));
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
            }
            return order;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForUserOrder, orderSet, connection);
            DBConnector.resourceCloser(psForDeliveryPerson, deliveryPersonSet, null);
            DBConnector.resourceCloser(psForOrderFoodItem, foodItemSet, null);
        }
    }

    @Override
    public void changeOrderStatus(int orderId, OrderStatus orderStatus) throws DBException {
        String orderSQL = """
                update order_ set order_status = ? where order_id = ?;
                """;
        try (Connection connection = DBConnector.getInstance().getConnection();
             PreparedStatement psForOrder = connection.prepareStatement(orderSQL)) {
            psForOrder.setString(1, orderStatus.name());
            psForOrder.setInt(2, orderId);
            psForOrder.execute();
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void assignDeliveryPerson(int orderId, int deliveryPersonId) throws DBException {
        String orderSQL = """
                update order_ set delivery_person_id = ? where order_id = ?;
                """;
        try (Connection connection = DBConnector.getInstance().getConnection();
             PreparedStatement psForOrder = connection.prepareStatement(orderSQL)) {
            psForOrder.setInt(1, deliveryPersonId);
            psForOrder.setInt(2, orderId);
            psForOrder.execute();
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Order getRecentOrderOfUser(int userId) throws DBException {
        String sql = """
                select o.order_id, o.total_price
                from user_ u, order_ o
                where u.user_id = o.user_id and o.order_date_time >= now() - interval 2 minute
                and o.order_status = 'ORDER_RECEIVED' and u.user_id = ?;
                """;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = new Order();
                order.setOrderId(resultSet.getInt(ORDER_ID));
                order.setTotalPrice(resultSet.getInt(TOTAL_PRICE));
            }
            return order;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, resultSet, connection);
        }
    }

    @Override
    public List<Order> getOrderAssignedToDP(int deliveryPersonId) throws DBException {
        String userOrderSQL = """
                select u.user_id, u.first_name, u.last_name, o.order_id, o.delivery_person_id,
                o.order_status, o.order_date_time, o.total_price, o.payment_status
                from user_ u, order_ o where u.user_id = o.user_id and o.order_status != 'DELIVERED'
                and o.order_status != 'CANCELLED' and o.delivery_person_id = ?;
                """;

        Connection connection = null;
        PreparedStatement psForUserOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        PreparedStatement psForDeliveryPerson = null;
        ResultSet orderSet = null;
        ResultSet foodItemSet = null;
        ResultSet deliveryPersonSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForUserOrder = connection.prepareStatement(userOrderSQL);
            psForUserOrder.setInt(1, deliveryPersonId);
            orderSet = psForUserOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (orderSet.next()) {
                User user = new User();
                user.setUserId(orderSet.getInt(USER_ID));
                user.setFirstName(orderSet.getString(FIRST_NAME));
                user.setLastName(orderSet.getString(LAST_NAME));
                User deliveryPerson = new User();
                deliveryPerson.setUserId(orderSet.getInt(DELIVERY_PERSON_ID));
                Order order = new Order();
                order.setOrderId(orderSet.getInt(ORDER_ID));
                order.setUser(user);
                order.setDeliveryPerson(deliveryPerson);
                order.setOrderStatus(OrderStatus.toEnum(orderSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(orderSet.getTimestamp(ORDER_DATE_TIME).toLocalDateTime());
                order.setTotalPrice(orderSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(orderSet.getString(PAYMENT_STATUS)));

                psForOrderFoodItem = connection.prepareStatement(ORDER_FOOD_ITEM_SQL);
                psForOrderFoodItem.setInt(1, orderSet.getInt(ORDER_ID));
                foodItemSet = psForOrderFoodItem.executeQuery();
                List<OrderFoodItems> orderFoodItemsList = new ArrayList<>();
                while (foodItemSet.next()) {
                    OrderFoodItems orderFoodItems = new OrderFoodItems();
                    FoodItem foodItem = new FoodItem();
                    foodItem.setFoodItemId(foodItemSet.getInt(FOOD_ITEM_ID));
                    foodItem.setFoodName(foodItemSet.getString(FOOD_NAME));
                    orderFoodItems.setFoodItem(foodItem);
                    orderFoodItems.setQuantity(foodItemSet.getInt(QUANTITY));
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForUserOrder, orderSet, connection);
            DBConnector.resourceCloser(psForDeliveryPerson, deliveryPersonSet, null);
            DBConnector.resourceCloser(psForOrderFoodItem, foodItemSet, null);
        }
    }

    @Override
    public List<Order> getCurrentOrderOfUser(int userId) throws DBException {
        String userOrderSQL = """
                select u.user_id, u.first_name, u.last_name, o.order_id, o.delivery_person_id,
                o.order_status, o.order_date_time, o.total_price, o.payment_status
                from user_ u, order_ o where u.user_id = o.user_id and o.order_status != 'DELIVERED'
                and o.order_status != 'CANCELLED' and o.user_id = ?;
                """;

        Connection connection = null;
        PreparedStatement psForUserOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        PreparedStatement psForDeliveryPerson = null;
        ResultSet orderSet = null;
        ResultSet foodItemSet = null;
        ResultSet deliveryPersonSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForUserOrder = connection.prepareStatement(userOrderSQL);
            psForUserOrder.setInt(1, userId);
            orderSet = psForUserOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (orderSet.next()) {
                User user = new User();
                user.setUserId(orderSet.getInt(USER_ID));
                user.setFirstName(orderSet.getString(FIRST_NAME));
                user.setLastName(orderSet.getString(LAST_NAME));
                psForDeliveryPerson = connection.prepareStatement(DELIVERY_PERSON_SQL);
                psForDeliveryPerson.setInt(1, orderSet.getInt(DELIVERY_PERSON_ID));
                deliveryPersonSet = psForDeliveryPerson.executeQuery();
                User deliveryPerson = new User();
                if (deliveryPersonSet.next()) {
                    deliveryPerson.setUserId(orderSet.getInt(DELIVERY_PERSON_ID));
                    deliveryPerson.setFirstName(deliveryPersonSet.getString(FIRST_NAME));
                    deliveryPerson.setLastName(deliveryPersonSet.getString(LAST_NAME));
                }
                Order order = new Order();
                order.setOrderId(orderSet.getInt(ORDER_ID));
                order.setUser(user);
                order.setDeliveryPerson(deliveryPerson);
                order.setOrderStatus(OrderStatus.toEnum(orderSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(orderSet.getTimestamp(ORDER_DATE_TIME).toLocalDateTime());
                order.setTotalPrice(orderSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(orderSet.getString(PAYMENT_STATUS)));

                psForOrderFoodItem = connection.prepareStatement(ORDER_FOOD_ITEM_SQL);
                psForOrderFoodItem.setInt(1, orderSet.getInt(ORDER_ID));
                foodItemSet = psForOrderFoodItem.executeQuery();
                List<OrderFoodItems> orderFoodItemsList = new ArrayList<>();
                while (foodItemSet.next()) {
                    OrderFoodItems orderFoodItems = new OrderFoodItems();
                    FoodItem foodItem = new FoodItem();
                    foodItem.setFoodItemId(foodItemSet.getInt(FOOD_ITEM_ID));
                    foodItem.setFoodName(foodItemSet.getString(FOOD_NAME));
                    foodItem.setPrice(foodItemSet.getDouble(PRICE));
                    foodItem.setImagePath(foodItemSet.getString(IMAGE_PATH));
                    orderFoodItems.setFoodItem(foodItem);
                    orderFoodItems.setQuantity(foodItemSet.getInt(QUANTITY));
                    orderFoodItems.setFoodItemsTotal(foodItemSet.getDouble(FOOD_ITEMS_TOTAL));
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForUserOrder, orderSet, connection);
            DBConnector.resourceCloser(psForDeliveryPerson, deliveryPersonSet, null);
            DBConnector.resourceCloser(psForOrderFoodItem, foodItemSet, null);
        }
    }
}
