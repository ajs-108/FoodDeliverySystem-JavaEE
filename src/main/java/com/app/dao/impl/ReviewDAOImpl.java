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

import static com.app.dao.impl.FoodItemDAOImpl.*;
import static com.app.dao.impl.OrderDAOImpl.ORDER_ID;
import static com.app.dao.impl.UserDAOImpl.FIRST_NAME;
import static com.app.dao.impl.UserDAOImpl.USER_ID;

public class ReviewDAOImpl implements IReviewDAO {
    protected static final String REVIEW_ID = "review_id";
    protected static final String REVIEW = "review";
    protected static final String RATING = "rating";

    @Override
    public int addReview(Review review) throws DBException {
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
            return preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Review> getAllReviews() throws DBException {
        String sql = """
                select r.review_id, u.user_id, u.first_name,
                fi.food_item_id, fi.food_name, fi.is_available, fi.image_path, r.order_id, r.rating, r.review
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
                foodItem.setIsAvailable(resultSet.getBoolean(IS_AVAILABLE));
                foodItem.setImagePath(resultSet.getString(IMAGE_PATH));
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
    public List<Review> getAllReviewsOfUser(int userId) throws DBException {
        String sql = """
                select r.review_id, u.user_id, u.first_name,
                fi.food_item_id, fi.food_name, fi.is_available, fi.image_path, r.order_id, r.rating, r.review
                from review_rating_table r, user_ u, food_item fi
                where u.user_id = r.user_id and fi.food_item_id = r.food_item_id and r.user_id = ?
                """;
        ResultSet resultSet = null;
        try (Connection connect = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            List<Review> reviewList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodItemId(resultSet.getInt(FOOD_ITEM_ID));
                foodItem.setFoodName(resultSet.getString(FOOD_NAME));
                foodItem.setIsAvailable(resultSet.getBoolean(IS_AVAILABLE));
                foodItem.setImagePath(resultSet.getString(IMAGE_PATH));
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
    public List<Double> getFoodRatings(int foodItemId) throws DBException {
        String sql = """
                select r.rating
                from review_rating_table r, food_item fi
                where fi.food_item_id = r.food_item_id and r.food_item_id = ?;
                """;
        ResultSet resultSet = null;
        try (Connection connection = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, foodItemId);
            resultSet = preparedStatement.executeQuery();
            List<Double> ratingList = new ArrayList<>();
            while (resultSet.next()) {
                ratingList.add(resultSet.getDouble(RATING));
            }
            return ratingList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(null, resultSet, null);
        }
    }

    @Override
    public Review getReview(int reviewId) throws DBException {
        String sql = """
                select r.review_id, u.user_id, u.first_name,
                fi.food_item_id, fi.food_name, fi.is_available, fi.image_path, r.order_id, r.rating, r.review
                from review_rating_table r, user_ u, food_item fi
                where u.user_id = r.user_id and fi.food_item_id = r.food_item_id and review_id = ?
                """;
        ResultSet resultSet = null;
        try (Connection connect = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, reviewId);
            resultSet = preparedStatement.executeQuery();
            Review review = null;
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodItemId(resultSet.getInt(FOOD_ITEM_ID));
                foodItem.setFoodName(resultSet.getString(FOOD_NAME));
                foodItem.setIsAvailable(resultSet.getBoolean(IS_AVAILABLE));
                foodItem.setImagePath(resultSet.getString(IMAGE_PATH));
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
        } finally {
            DBConnector.resourceCloser(null, resultSet, null);
        }
    }

    @Override
    public Review getReview(int userId, int foodItemId, int orderId) throws DBException {
        String sql = """
                select review_id, rating
                from review_rating_table
                where user_Id = ? and food_item_id = ? and order_id = ?
                """;
        ResultSet resultSet = null;
        try (Connection connect = DBConnector.getInstance().getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, foodItemId);
            preparedStatement.setInt(3, orderId);
            resultSet = preparedStatement.executeQuery();
            Review review = null;
            if (resultSet.next()) {
                review = new Review();
                review.setReviewId(resultSet.getInt(REVIEW_ID));
                review.setRating(resultSet.getInt(RATING));
            }
            return review;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            DBConnector.resourceCloser(null, resultSet, null);
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
