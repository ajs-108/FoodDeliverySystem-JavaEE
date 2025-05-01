package com.app.controller.jpa.review;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.JPAuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.controller.common.validation.QueryParameterValidator;
import com.app.controller.common.validation.ReviewValidator;
import com.app.dto.common.APIResponse;
import com.app.dto.jdbc.ReviewDTO;
import com.app.dto.jpa.review.GetReviewDTO;
import com.app.service.jdbc.ReviewServices;
import com.app.service.jpa.JPAReviewServices;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "get-review", value = "/get-review")
public class GetReviewController extends HttpServlet {
    private JPAReviewServices reviewServices = new JPAReviewServices();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            JPAuthUtils.checkAuthentication(request);
            QueryParameterValidator.validate(request, "reviewId");
            String reviewId = request.getParameter("reviewId");
            ReviewValidator.validateGetReview(reviewId);
            GetReviewDTO reviewDTO = reviewServices.findById(Integer.parseInt(reviewId));
            sendResponse(response, null, null, reviewDTO, HttpServletResponse.SC_OK);
        } catch (DBException e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ApplicationException e) {
            e.printStackTrace();
            sendResponse(response, null, e.getMessage(), null, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response, e.getMessage(), Message.Error.GENERIC_ERROR, null, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void sendResponse(HttpServletResponse response, String techMessage, String message, Object data, int status) throws IOException {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        response.setStatus(status);
        response.getWriter().write(ObjectMapperUtil.toString(apiResponse));
    }
}