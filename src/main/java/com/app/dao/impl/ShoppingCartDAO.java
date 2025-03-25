package com.app.dao.impl;

import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.IShoppingCartDAO;
import com.app.model.ShoppingCart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShoppingCartDAO implements IShoppingCartDAO {
    @Override
    public void addFoodItem(ShoppingCart shoppingCart) throws DBException {
        String sql = """
                insert into shopping_cart
                values (?,?,?);
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, shoppingCart.getFoodItem().getFoodItemId());
            preparedStatement.setInt(2, shoppingCart.getUser().getUserId());
            preparedStatement.setInt(3, shoppingCart.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void removeFoodItem(int foodItemId) throws DBException {
        String sql = """
                delete from shopping_cart
                where food_item_id = ?;
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, foodItemId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateQuantity(int foodItemId, int quantity) throws DBException{
        String sql = """
                update shopping_cart
                set quantity = ?
                where food_item_id = ?;
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, foodItemId);
            preparedStatement.setInt(2, quantity);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }
}
