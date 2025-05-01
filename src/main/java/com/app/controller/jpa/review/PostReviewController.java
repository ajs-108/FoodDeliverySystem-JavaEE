package com.app.controller.jpa.review;

import com.app.common.AppConstant;
import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.common.util.AuthUtils;
import com.app.common.util.ObjectMapperUtil;
import com.app.dto.common.APIResponse;
import com.app.dto.jdbc.ReviewDTO;
import com.app.dto.jpa.review.GetReviewDTO;
import com.app.dto.jpa.review.JPAReviewDTO;
import com.app.service.jdbc.ReviewServices;
import com.app.service.jpa.JPAReviewServices;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "post-review", value = "/post-review")
public class PostReviewController extends HttpServlet {
    private JPAReviewServices reviewServices = new JPAReviewServices();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(AppConstant.APPLICATION_JSON);
        try {
            AuthUtils.checkAuthentication(request);
            List<JPAReviewDTO> reviewDTOList = ObjectMapperUtil.toObject(request.getReader(), new TypeReference<>() {
            });
            reviewServices.save(reviewDTOList);
            sendResponse(response, null, Message.Review.REVIEW_POSTED, null, HttpServletResponse.SC_CREATED);
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