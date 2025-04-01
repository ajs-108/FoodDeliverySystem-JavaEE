package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.enums.OrderStatus;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.OrderDTO;
import com.app.service.UserServices;

import java.util.Objects;

public class OrderValidator {
    private static UserServices userServices = new UserServices();
    private static Validator validator = new Validator();

    public static void validateOrder(int userId, OrderDTO orderDTO) throws DBException, ApplicationException {
        if (userId == 0) {
            throw new ApplicationException(Message.Error.GENERIC_ERROR);
        }
        if (userServices.getUser(userId) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
    }

    public static void validateUpdateStatus(String orderId, OrderStatus orderStatus) throws ApplicationException {
        if (orderId == null || orderId.isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validator.isPositiveInteger(orderId)) {
            throw new ApplicationException(Message.Common.NOT_A_POSITIVE_INTEGER);
        }
        if (Objects.equals(orderId,"0")) {
            throw new ApplicationException(Message.Common.GT_ZERO);
        }
        if (orderStatus == null) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
    }
}
