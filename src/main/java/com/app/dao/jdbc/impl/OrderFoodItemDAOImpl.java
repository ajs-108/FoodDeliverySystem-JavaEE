package com.app.dao.jdbc.impl;

import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.jdbc.IOrderFoodItemsDAO;
import com.app.model.jdbc.CartFoodItems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderFoodItemDAOImpl implements IOrderFoodItemsDAO {

    @Override
    public int addFoodItem(CartFoodItems cartFoodItems, int orderId) throws DBException {
        String sql = """
                insert into order_food_items(food_item_id, order_id, quantity, food_items_total)
                values(?, ?, ?, ?)
                """;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cartFoodItems.getFoodItem().getFoodItemId());
            preparedStatement.setInt(2, orderId);
            preparedStatement.setInt(3, cartFoodItems.getQuantity());
            preparedStatement.setDouble(4, cartFoodItems.getAfterDiscountPrice());
            return preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, null, connection);
        }
    }
}
