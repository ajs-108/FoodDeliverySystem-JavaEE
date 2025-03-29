package com.app.dao.impl;

import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;
import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.IOrderDAO;
import com.app.model.Order;
import com.app.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements IOrderDAO {
    @Override
    public void placeOrder(int userId, Order order) throws DBException {
        String orderSQL = """
                insert into order_(user_id, total_price, order_status, payment_status)
                select u.user_id, (sum(fi.price*sc.quantity)), ?, ?
                from shopping_cart sc, food_item fi, user_ u
                where sc.user_id = u.user_id and fi.food_item_id = sc.food_item_id and u.user_id = ?;
                """;
        String orderFoodItemSQL = """
                insert into order_food_items(food_item_id, order_id, quantity)
                select sc.food_item_id, o.order_id, sc.quantity
                from shopping_cart sc, order_ o where sc.user_id = o.user_id and order_id = ?;
                """;
        Connection connection = null;
        PreparedStatement psForOrder = null;
        ResultSet setOfGeneratedKeys = null;
        PreparedStatement psForOrderFoodItems = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            connection.setAutoCommit(false);
            psForOrder = connection.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS);
            psForOrder.setString(1, order.getOrderStatus().name());
            psForOrder.setString(2, order.getPaymentStatus().name());
            psForOrder.setInt(3, userId);
            psForOrder.executeUpdate();
            setOfGeneratedKeys = psForOrder.getGeneratedKeys();
            int generatedKey = 0;
            if(setOfGeneratedKeys.next()) {
                generatedKey = setOfGeneratedKeys.getInt(1);
            }
            psForOrderFoodItems = connection.prepareStatement(orderFoodItemSQL);
            psForOrderFoodItems.setInt(1, generatedKey);
            psForOrderFoodItems.execute();
            connection.commit();
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            try {
                connection.rollback();
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
        String orderSQL = """
                select * from order_;
                """;
        Connection connection = null;
        PreparedStatement psForOrder = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            psForOrder = connection.prepareStatement(orderSQL);
            resultSet = psForOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while(resultSet.next()) {
                Order order = new Order();
                User user = new User();
                order.setOrderId(resultSet.getInt("order_id"));
                user.setUserId(resultSet.getInt("user_id"));
                order.setUser(user);
                order.setOrderStatus(OrderStatus.toEnum(resultSet.getString("order_status")));
                order.setOrderDateTime(resultSet.getTimestamp("order_date_time").toLocalDateTime());
                order.setTotalPrice(resultSet.getInt("total_price"));
                order.setPaymentStatus(PaymentStatus.toEnum(resultSet.getString("payment_status")));
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForOrder, resultSet, connection);
        }
    }
}
