package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.IReviewRepository;
import com.app.dao.jpa.impl.ReviewRepository;
import com.app.dto.jpa.review.GetReviewDTO;
import com.app.dto.jpa.review.JPAReviewDTO;
import com.app.mapper.jpa.JPAReviewMapper;
import com.app.model.common.FoodItem;
import com.app.model.jpa.JPAReview;
import com.app.service.common.FoodItemServices;

import java.util.List;

public class JPAReviewServices {
    private IReviewRepository reviewRepo;
    private JPAReviewMapper reviewMapper;
    private JPAUserServices userServices;
    private FoodItemServices foodItemServices;
    private JPAOrderServices orderServices;

    public JPAReviewServices() {
        reviewRepo = new ReviewRepository();
        reviewMapper = new JPAReviewMapper();
        userServices = new JPAUserServices();
        foodItemServices = new FoodItemServices();
        orderServices = new JPAOrderServices();
    }

    public void save(List<JPAReviewDTO> reviewDTOList) throws DBException {
        for (JPAReviewDTO reviewDTO : reviewDTOList) {
            //TODO: Make validation for this
            reviewDTO.setUser(userServices.find(reviewDTO.getUser().getUserId()));
            reviewDTO.setFoodItem(foodItemServices.getFoodItem(reviewDTO.getFoodItem().getFoodItemId()));
            reviewDTO.setOrder(orderServices.find(reviewDTO.getOrder().getOrderId()));
            reviewRepo.save(reviewMapper.toReview(reviewDTO));
        }
    }

    public List<GetReviewDTO> findAll() throws DBException {
        return reviewRepo.findAll();
    }

    public List<GetReviewDTO> findAllReviewOfUser(int userId) throws DBException {
        return reviewRepo.findAllReviewOfUser(userId);
    }

    public GetReviewDTO findById(int reviewId) throws DBException {
        return reviewRepo.findById(reviewId);
    }

    public GetReviewDTO findByIds(int userId, int foodItemId, int orderId) throws DBException {
        return reviewRepo.findByIds(userId, foodItemId, orderId);
    }

    public void update(JPAReview review) throws DBException{
        reviewRepo.update(review);
    }

    public void delete(JPAReview review) throws DBException {
        reviewRepo.delete(review);
    }
}
