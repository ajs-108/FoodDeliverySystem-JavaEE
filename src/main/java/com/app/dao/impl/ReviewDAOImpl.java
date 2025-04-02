package com.app.dao.impl;

import com.app.common.exception.DBException;
import com.app.config.DBConnector;
import com.app.dao.IReviewDAO;
import com.app.model.FoodItem;
import com.app.model.Review;
import com.app.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements IReviewDAO {
    @Override
    public void addReview(Review review) throws DBException {
        String sql = """
                insert into review_rating_table(user_id, food_item_id, order_id, rating, review)
                values(?,?,?,?,?);
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, review.getUser().getUserId());
            preparedStatement.setInt(2, review.getFoodItem().getFoodItemId());
            preparedStatement.setInt(3, review.getOrderId());
            preparedStatement.setDouble(4, review.getRating());
            preparedStatement.setString(5, review.getUserReview());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Review> getAllReview() throws DBException {
        String sql = """
                select r.review_id, u.user_id, u.first_name,
                fi.food_item_id, fi.food_name, r.order_id, r.rating, r.review
                from review_rating_table r, user_ u, food_item fi
                where u.user_id = r.user_id and fi.food_item_id = r.food_item_id;
                """;
        ResultSet resultSet = null;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            resultSet = preparedStatement.executeQuery();
            List<Review> reviewList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodItemId(resultSet.getInt("food_item_id"));
                foodItem.setFoodName(resultSet.getString("food_name"));
                Review review = new Review();
                review.setReviewId(resultSet.getInt("review_id"));
                review.setUser(user);
                review.setFoodItem(foodItem);
                review.setOrderId(resultSet.getInt("order_id"));
                review.setRating(resultSet.getInt("rating"));
                review.setUserReview(resultSet.getString("review"));
                reviewList.add(review);
            }
            return reviewList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }
}
