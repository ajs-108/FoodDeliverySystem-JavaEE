package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.ReviewDTO;
import com.app.service.FoodItemServices;
import com.app.service.OrderServices;
import com.app.service.ReviewServices;
import com.app.service.UserServices;

public class ReviewValidator {
    private static final int REVIEW_LENGTH = 255;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;
    private static ReviewServices reviewServices = new ReviewServices();
    private static UserServices userServices = new UserServices();
    private static FoodItemServices foodItemServices = new FoodItemServices();
    private static OrderServices orderServices = new OrderServices();
    private static Validator validator = new Validator();

    private ReviewValidator() {
    }

    public static void validateReview(ReviewDTO reviewDTO) throws ApplicationException, DBException {
        if (userServices.getUser(reviewDTO.getUserDTO().getUserId()) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if (!foodItemServices.isFoodItemExists(reviewDTO.getFoodItemDTO().getFoodItemId())) {
            throw new ApplicationException(Message.FoodItem.FOOD_ITEM_DOES_NOT_EXISTS);
        }
        if (orderServices.getOrder(reviewDTO.getOrderId()) == null) {
            throw new ApplicationException(Message.Order.ORDER_DOES_NOT_EXISTS);
        }
        if (reviewDTO.getRating() < MIN_RATING || reviewDTO.getRating() > MAX_RATING) {
            throw new ApplicationException(Message.Review.INVALID_RATING);
        }
        if (reviewDTO.getReview().length() >= REVIEW_LENGTH) {
            throw new ApplicationException(Message.Review.REVIEW_LENGTH_EXCEEDED);
        }
        if (reviewServices.getReview(reviewDTO.getUserDTO().getUserId(),
                reviewDTO.getFoodItemDTO().getFoodItemId(),
                reviewDTO.getOrderId()) != null) {
            throw  new ApplicationException(Message.Review.REVIEW_EXISTS);
        }
    }

    public static void validateGetReview(String reviewId) throws ApplicationException, DBException {
        if (!validator.isPositiveInteger(reviewId)) {
            throw new ApplicationException(Message.Common.NOT_A_POSITIVE_INTEGER);
        }
        if (reviewServices.getReview(Integer.parseInt(reviewId)) == null) {
            throw new ApplicationException(Message.Review.REVIEW_DOES_NOT_EXISTS);
        }
    }
}
