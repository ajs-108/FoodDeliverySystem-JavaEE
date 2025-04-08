package com.app.dao.impl;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;
import com.app.common.enums.Roles;
import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.IOrderDAO;
import com.app.model.FoodItem;
import com.app.model.Order;
import com.app.model.OrderFoodItems;
import com.app.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.app.dao.impl.FoodItemDAOImpl.*;
import static com.app.dao.impl.ShoppingCartDAOImpl.QUANTITY;
import static com.app.dao.impl.UserDAOImpl.USER_ID;

public class OrderDAOImpl implements IOrderDAO {
    protected static final String ORDER_ID = "order_id";
    protected static final String DELIVERY_PERSON_ID = "delivery_person_id";
    protected static final String TOTAL_PRICE = "total_price";
    protected static final String ORDER_STATUS = "order_status";
    protected static final String ORDER_DATE_TIME = "order_date_time";
    protected static final String PAYMENT_STATUS = "payment_status";
    private static final String ORDER_FOOD_ITEM_SQL = """
            select * from order_food_items ofi, food_item fi
            where order_id = ? and fi.food_item_id = ofi.food_item_id
            """;

    @Override
    public int placeOrder(int userId, Order order) throws DBException {
        String orderSQL = """
                insert into order_(user_id, total_price, order_status, payment_status)
                select u.user_id, ?, ?, ?
                from user_ u where u.user_id = ?
                """;
        String orderFoodItemSQL = """
                insert into order_food_items(food_item_id, order_id, quantity)
                select sc.food_item_id, o.order_id, sc.quantity
                from shopping_cart sc, order_ o where sc.user_id = o.user_id and order_id = ?
                """;
        Connection connection = null;
        PreparedStatement psForOrder = null;
        ResultSet setOfGeneratedKeys = null;
        PreparedStatement psForOrderFoodItems = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            connection.setAutoCommit(false);
            psForOrder = connection.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS);
            psForOrder.setDouble(1, order.getTotalPrice());
            psForOrder.setString(2, order.getOrderStatus().name());
            psForOrder.setString(3, order.getPaymentStatus().name());
            psForOrder.setInt(4, userId);
            int i = psForOrder.executeUpdate();
            setOfGeneratedKeys = psForOrder.getGeneratedKeys();
            int generatedKey = 0;
            if (setOfGeneratedKeys.next()) {
                generatedKey = setOfGeneratedKeys.getInt(1);
            }
            psForOrderFoodItems = connection.prepareStatement(orderFoodItemSQL);
            psForOrderFoodItems.setInt(1, generatedKey);
            i += psForOrderFoodItems.executeUpdate();
            connection.commit();
            return i;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException | NullPointerException ex) {
                throw new DBException(ex);
            }
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForOrder, setOfGeneratedKeys, connection);
            DBConnector.resourceCloser(psForOrderFoodItems, null, null);
        }
    }

    @Override
    public List<Order> getAllOrder() throws DBException {
        String orderSQL = "select * from order_";
        Connection connection = null;
        PreparedStatement psForOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        ResultSet resultSet = null;
        ResultSet foodItemSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForOrder = connection.prepareStatement(orderSQL);
            resultSet = psForOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                User user = new User();
                order.setOrderId(resultSet.getInt(ORDER_ID));
                user.setUserId(resultSet.getInt(USER_ID));
                order.setUser(user);
                order.setOrderStatus(OrderStatus.toEnum(resultSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(resultSet.getTimestamp(ORDER_DATE_TIME).toLocalDateTime());
                order.setTotalPrice(resultSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(resultSet.getString(PAYMENT_STATUS)));
                psForOrderFoodItem = connection.prepareStatement(ORDER_FOOD_ITEM_SQL);
                psForOrderFoodItem.setInt(1, resultSet.getInt(ORDER_ID));
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
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForOrder, resultSet, connection);
            DBConnector.resourceCloser(psForOrderFoodItem, foodItemSet, null);
        }
    }

    @Override
    public List<Order> getAllOrder(int userId, Roles roles) throws DBException {
        String users = "";
        if (roles == Roles.ROLE_DELIVERY_PERSON) {
            users = DELIVERY_PERSON_ID;
        }
        if (roles == Roles.ROLE_CUSTOMER) {
            users = USER_ID;
        }
        String orderSQL = String.format("select * from order_ where %s = ?", users);
        Connection connection = null;
        PreparedStatement psForOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        ResultSet resultSet = null;
        ResultSet foodItemSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForOrder = connection.prepareStatement(orderSQL);
            psForOrder.setInt(1, userId);
            resultSet = psForOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                User user = new User();
                order.setOrderId(resultSet.getInt(ORDER_ID));
                user.setUserId(resultSet.getInt(USER_ID));
                order.setUser(user);
                order.setOrderStatus(OrderStatus.toEnum(resultSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(resultSet.getTimestamp(ORDER_DATE_TIME).toLocalDateTime());
                order.setTotalPrice(resultSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(resultSet.getString(PAYMENT_STATUS)));
                psForOrderFoodItem = connection.prepareStatement(ORDER_FOOD_ITEM_SQL);
                psForOrderFoodItem.setInt(1, resultSet.getInt(ORDER_ID));
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
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForOrder, resultSet, connection);
            DBConnector.resourceCloser(psForOrderFoodItem, foodItemSet, null);
        }
    }

    @Override
    public List<Order> getAllOrder(OrderStatus orderStatus) throws DBException {
        String orderSQL = "select * from order_ where order_status = ?";

        Connection connection = null;
        PreparedStatement psForOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        ResultSet resultSet = null;
        ResultSet foodItemSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForOrder = connection.prepareStatement(orderSQL);
            psForOrder.setString(1, orderStatus.name());
            resultSet = psForOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                User user = new User();
                order.setOrderId(resultSet.getInt(ORDER_ID));
                user.setUserId(resultSet.getInt(USER_ID));
                order.setUser(user);
                order.setOrderStatus(OrderStatus.toEnum(resultSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(resultSet.getTimestamp(ORDER_STATUS).toLocalDateTime());
                order.setTotalPrice(resultSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(resultSet.getString(PAYMENT_STATUS)));
                psForOrderFoodItem = connection.prepareStatement(ORDER_FOOD_ITEM_SQL);
                psForOrderFoodItem.setInt(1, resultSet.getInt(ORDER_ID));
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
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForOrder, resultSet, connection);
            DBConnector.resourceCloser(psForOrderFoodItem, foodItemSet, null);
        }
    }

    @Override
    public Order getOrder(int orderId, int userId) throws DBException {
        String orderSQL = """
                select * from order_
                where order_id = ? and user_id = ?
                """;
        Connection connection = null;
        PreparedStatement psForOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        ResultSet resultSet = null;
        ResultSet foodItemSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForOrder = connection.prepareStatement(orderSQL);
            psForOrder.setInt(1, orderId);
            psForOrder.setInt(2, userId);
            resultSet = psForOrder.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = new Order();
                User user = new User();
                order.setOrderId(resultSet.getInt(ORDER_ID));
                user.setUserId(resultSet.getInt(USER_ID));
                order.setUser(user);
                order.setOrderStatus(OrderStatus.toEnum(resultSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(resultSet.getTimestamp(ORDER_DATE_TIME).toLocalDateTime());
                order.setTotalPrice(resultSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(resultSet.getString(PAYMENT_STATUS)));
                psForOrderFoodItem = connection.prepareStatement(ORDER_FOOD_ITEM_SQL);
                psForOrderFoodItem.setInt(1, resultSet.getInt(ORDER_ID));
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
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
            }
            return order;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForOrder, resultSet, connection);
            DBConnector.resourceCloser(psForOrderFoodItem, foodItemSet, null);
        }
    }

    @Override
    public Order getOrder(int orderId) throws DBException {
        String orderSQL = """
                select * from order_
                where order_id = ?;
                """;
        String orderFoodItemSQL = """
                select * from order_food_items ofi, food_item fi
                where order_id = ? and fi.food_item_id = ofi.food_item_id;
                """;
        Connection connection = null;
        PreparedStatement psForOrder = null;
        PreparedStatement psForOrderFoodItem = null;
        ResultSet resultSet = null;
        ResultSet foodItemSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForOrder = connection.prepareStatement(orderSQL);
            psForOrder.setInt(1, orderId);
            resultSet = psForOrder.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = new Order();
                User user = new User();
                order.setOrderId(resultSet.getInt(ORDER_ID));
                user.setUserId(resultSet.getInt(USER_ID));
                order.setUser(user);
                order.setOrderStatus(OrderStatus.toEnum(resultSet.getString(ORDER_STATUS)));
                order.setOrderDateTime(resultSet.getTimestamp(ORDER_DATE_TIME).toLocalDateTime());
                order.setTotalPrice(resultSet.getInt(TOTAL_PRICE));
                order.setPaymentStatus(PaymentStatus.toEnum(resultSet.getString(PAYMENT_STATUS)));
                psForOrderFoodItem = connection.prepareStatement(orderFoodItemSQL);
                psForOrderFoodItem.setInt(1, resultSet.getInt(ORDER_ID));
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
                    orderFoodItemsList.add(orderFoodItems);
                }
                order.setOrderFoodItems(orderFoodItemsList);
            }
            return order;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForOrder, resultSet, connection);
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
}
