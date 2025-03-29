package com.app.dao.impl;

import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.IOrderDAO;
import com.app.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            assert false;
            connection.setAutoCommit(false);
            connection = DBConnector.getInstance().getConnection();
            psForOrder = connection.prepareStatement(orderSQL);
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
            } catch (SQLException ex) {
                throw new DBException(ex);
            }
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(psForOrder, setOfGeneratedKeys, connection);
            DBConnector.resourceCloser(psForOrderFoodItems, null, null);
        }
    }
}
