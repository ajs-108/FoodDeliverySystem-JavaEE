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

import static com.app.dao.impl.FoodItemDAOImpl.FOOD_ITEM_ID;
import static com.app.dao.impl.FoodItemDAOImpl.FOOD_NAME;
import static com.app.dao.impl.OrderDAOImpl.ORDER_ID;
import static com.app.dao.impl.UserDAOImpl.FIRST_NAME;
import static com.app.dao.impl.UserDAOImpl.USER_ID;

public class ReviewDAOImpl implements IReviewDAO {
    protected static final String REVIEW_ID = "review_id";
    protected static final String REVIEW = "review";
    protected static final String RATING = "rating";

    @Override
    public void addReview(Review review) throws DBException {
        String sql = """
                insert into review_rating_table(user_id, food_item_id, order_id, rating, review)
                values(?,?,?,?,?)
                """;
        try (Connection connect = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
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
                where u.user_id = r.user_id and fi.food_item_id = r.food_item_id
                """;
        try (Connection connect = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Review> reviewList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodItemId(resultSet.getInt(FOOD_ITEM_ID));
                foodItem.setFoodName(resultSet.getString(FOOD_NAME));
                Review review = new Review();
                review.setReviewId(resultSet.getInt(REVIEW_ID));
                review.setUser(user);
                review.setFoodItem(foodItem);
                review.setOrderId(resultSet.getInt(ORDER_ID));
                review.setRating(resultSet.getInt(RATING));
                review.setUserReview(resultSet.getString(REVIEW));
                reviewList.add(review);
            }
            return reviewList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Review getReview(int reviewId) throws DBException {
        String sql = """
                select r.review_id, u.user_id, u.first_name,
                fi.food_item_id, fi.food_name, r.order_id, r.rating, r.review
                from review_rating_table r, user_ u, food_item fi
                where u.user_id = r.user_id and fi.food_item_id = r.food_item_id
                """;
        try (Connection connect = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            Review review = null;
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodItemId(resultSet.getInt(FOOD_ITEM_ID));
                foodItem.setFoodName(resultSet.getString(FOOD_NAME));
                review = new Review();
                review.setReviewId(resultSet.getInt(REVIEW_ID));
                review.setUser(user);
                review.setFoodItem(foodItem);
                review.setOrderId(resultSet.getInt(ORDER_ID));
                review.setRating(resultSet.getInt(RATING));
                review.setUserReview(resultSet.getString(REVIEW));
            }
            return review;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateReview(Review review) throws DBException {
        String sql = """
                update review_rating_table set rating = ?, review = ?
                where review_id = ?
                """;
        try (Connection connect = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, review.getRating());
            preparedStatement.setString(2, review.getUserReview());
            preparedStatement.setInt(3, review.getReviewId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void deleteReview(int reviewId) throws DBException {
        String sql = "delete from review_rating_table where review_id = ?";
        try (Connection connect = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, reviewId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }
}
