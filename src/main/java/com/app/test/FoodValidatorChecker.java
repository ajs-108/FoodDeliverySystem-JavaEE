package com.app.test;

import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.service.FoodItemServices;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodValidatorChecker {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, DBException {
        Connection connection = DBConnector.getInstance("jdbc:mysql://localhost:3306/food_delivery_db", "com.mysql.cj.jdbc.Driver",
                "root", "password123#").getConnection();

        String sql = """
                select r.rating
                from review_rating_table r, food_item fi
                where fi.food_item_id = r.food_item_id and r.food_item_id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 6);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Double> ratingList = new ArrayList<>();
        while (resultSet.next()) {
            ratingList.add(resultSet.getDouble("rating"));
        }
        FoodItemServices foodItemServices = new FoodItemServices();
        foodItemServices.updateRatings(6, ratingList);
    }
}
