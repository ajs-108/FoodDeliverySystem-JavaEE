package com.app.service;

import com.app.common.exception.DBException;
import com.app.controller.validation.ReviewValidator;
import com.app.dao.IReviewDAO;
import com.app.dao.impl.ReviewDAOImpl;
import com.app.dto.ReviewDTO;
import com.app.mapper.ReviewMapper;

import java.util.List;

public class ReviewServices {
    private IReviewDAO reviewDAO;
    private ReviewMapper reviewMapper;

    public ReviewServices() {
        this.reviewDAO = new ReviewDAOImpl();
        this.reviewMapper = new ReviewMapper();
    }

    public void postReview(List<ReviewDTO> reviewDTOList) throws DBException {
        for (ReviewDTO reviewDTO : reviewDTOList) {
            ReviewValidator.validateReview(reviewDTO);
            reviewDAO.addReview(reviewMapper.toReview(reviewDTO));
        }
    }

    public List<ReviewDTO> getAllReview() throws DBException {
        return reviewDAO.getAllReview()
                .stream()
                .map(reviewMapper::toDTO)
                .toList();
    }
}
