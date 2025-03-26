package com.app.dao.impl;

import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.IShoppingCartDAO;
import com.app.model.FoodItem;
import com.app.model.ShoppingCart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDAO implements IShoppingCartDAO {
    @Override
    public void addFoodItem(ShoppingCart shoppingCart) throws DBException {
        String sql = """
                insert into shopping_cart
                values (?,?,?);
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, shoppingCart.getFoodItem().getFoodItemId());
            preparedStatement.setInt(2, shoppingCart.getUserId());
            preparedStatement.setInt(3, shoppingCart.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void removeFoodItem(int userId, int foodItemId) throws DBException {
        String sql = """
                delete from shopping_cart
                where user_id = ? and food_item_id = ?;
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, foodItemId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateQuantity(ShoppingCart shoppingCart) throws DBException {
        String sql = """
                update shopping_cart
                set quantity = ?
                where user_id = ? and food_item_id = ?;
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, shoppingCart.getQuantity());
            preparedStatement.setInt(2, shoppingCart.getUserId());
            preparedStatement.setInt(3, shoppingCart.getFoodItem().getFoodItemId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<ShoppingCart> getShoppingCart(int userId) throws DBException {
        String sql = """
                select u.user_id, fi.food_item_id, fi.food_name, fi.food_description, fi.price, fi.image_path, sc.quantity
                from shopping_cart as sc, user_ as u, food_item as fi
                where sc.user_id = u.user_id and fi.food_item_id = sc.food_item_id and u.user_id = ?;
                """;
        ResultSet resultSet = null;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            List<ShoppingCart> shoppingCartList = new ArrayList<>();
            while(resultSet.next()) {
                ShoppingCart shoppingCart = new ShoppingCart();
                FoodItem foodItem = new FoodItem();
                shoppingCart.setUserId(resultSet.getInt("user_id"));
                foodItem.setFoodItemId(resultSet.getInt("food_item_id"));
                foodItem.setFoodName(resultSet.getString("food_name"));
                foodItem.setFoodDescription(resultSet.getString("food_description"));
                foodItem.setPrice(resultSet.getDouble("price"));
                foodItem.setImagePath(resultSet.getString("image_path"));
                shoppingCart.setFoodItem(foodItem);
                shoppingCart.setQuantity(resultSet.getInt("quantity"));
                shoppingCartList.add(shoppingCart);
            }
            return shoppingCartList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(null, resultSet, null);
        }
    }
}
