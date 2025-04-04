package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;
import com.app.common.enums.Roles;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.OrderDTO;
import com.app.dto.UserDTO;
import com.app.service.OrderServices;
import com.app.service.ShoppingCartServices;
import com.app.service.UserServices;

import java.util.Objects;

public class OrderValidator {
    private static UserServices userServices = new UserServices();
    private static ShoppingCartServices shoppingCartServices = new ShoppingCartServices();
    private static OrderServices orderServices = new OrderServices();
    private static Validator validator = new Validator();

    public static void validateOrder(int userId, OrderDTO orderDTO) throws DBException, ApplicationException {
        if (userServices.getUser(userId) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
        if (orderDTO.getTotalPrice() != shoppingCartServices.showShoppingCart(userId).getTotalPrice()) {
            throw new ApplicationException(Message.Order.INCORRECT_TOTAL_PRICE);
        }
        if (orderDTO.getOrderStatus() == null) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!OrderStatus.isOrderStatus(orderDTO.getOrderStatus().name())) {
            throw new ApplicationException(Message.Order.NOT_A_ORDER_STATUS);
        }
        if (orderDTO.getPaymentStatus() == null) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!PaymentStatus.isPaymentStatus(orderDTO.getPaymentStatus().name())) {
            throw new ApplicationException(Message.Order.NOT_A_PAYMENT_STATUS);
        }
    }

    public static void validateUpdateStatus(String orderId, String orderStatus)
            throws ApplicationException, DBException {
        if (orderId == null || orderId.isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validator.isPositiveInteger(orderId)) {
            throw new ApplicationException(Message.Common.NOT_A_POSITIVE_INTEGER);
        }
        if (orderServices.getOrder(Integer.parseInt(orderId)) == null) {
            throw new ApplicationException(Message.Order.ORDER_DOES_NOT_EXISTS);
        }
        if (orderStatus == null) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!OrderStatus.isOrderStatus(orderStatus)) {
            throw new ApplicationException(Message.Order.NOT_A_ORDER_STATUS);
        }
    }

    public static void validateAssignDeliveryPerson(String orderId, String deliveryPersonId, String roles)
            throws ApplicationException, DBException {
        if (orderId == null || orderId.isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validator.isPositiveInteger(orderId)) {
            throw new ApplicationException(Message.Common.NOT_A_POSITIVE_INTEGER);
        }
        if (orderServices.getOrder(Integer.parseInt(orderId)) == null) {
            throw new ApplicationException(Message.Order.ORDER_DOES_NOT_EXISTS);
        }
        if (deliveryPersonId == null || deliveryPersonId.isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!validator.isPositiveInteger(deliveryPersonId)) {
            throw new ApplicationException(Message.Common.NOT_A_POSITIVE_INTEGER);
        }
        if (userServices.getUser(Integer.parseInt(deliveryPersonId)) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_DELIVERY_PERSON);
        }
        if (!Objects.equals(roles, Roles.ROLE_DELIVERY_PERSON.name())) {
            throw new ApplicationException(Message.User.NOT_A_DELIVERY_PERSON);
        }
    }

    public static void validateGetOrdersByStatus(String orderStatus) throws ApplicationException, DBException {
        if (orderStatus == null) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!OrderStatus.isOrderStatus(orderStatus)) {
            throw new ApplicationException(Message.Order.NOT_A_ORDER_STATUS);
        }
    }

    public static void validateGetOrdersOfUser(UserDTO userDTO) throws ApplicationException, DBException {
        if (userServices.getUser(userDTO.getEmail()) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
    }
}
