package com.app.controller.common.validation;

import com.app.common.Message;
import com.app.common.enums.OrderStatus;
import com.app.common.enums.PaymentStatus;
import com.app.common.enums.Roles;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.dto.jdbc.OrderDTO;
import com.app.dto.jdbc.UserDTO;
import com.app.dto.jpa.JPAUserDTO;
import com.app.dto.jpa.order.JPAOrderDTO;
import com.app.service.jdbc.OrderServices;
import com.app.service.jdbc.ShoppingCartServices;
import com.app.service.jdbc.UserServices;

import java.util.Objects;

public class OrderValidator {
    private static final UserServices userServices = new UserServices();
    private static final OrderServices orderServices = new OrderServices();
    private static final ShoppingCartServices cartServices = new ShoppingCartServices();
    private static final Validator validator = new Validator();

    private OrderValidator() {
    }

    public static void validatePlaceOrder(int userId, OrderDTO orderDTO) throws DBException, ApplicationException {
        if (userServices.getUser(userId) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
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
        if (cartServices.isCartEmpty(userId)) {
            throw new ApplicationException(Message.ShoppingCart.CART_IS_EMPTY);
        }
    }

    public static void validatePlaceOrder(int userId, JPAOrderDTO orderDTO) throws DBException, ApplicationException {
        if (userServices.getUser(userId) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
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
        if (cartServices.isCartEmpty(userId)) {
            throw new ApplicationException(Message.ShoppingCart.CART_IS_EMPTY);
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
        if (orderStatus == null || orderStatus.isBlank()) {
            throw new ApplicationException(Message.Common.MANDATORY);
        }
        if (!OrderStatus.isOrderStatus(orderStatus)) {
            throw new ApplicationException(Message.Order.NOT_A_ORDER_STATUS);
        }
    }

    public static void validateAssignDeliveryPerson(String orderId, String deliveryPersonId)
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
        UserDTO userDTO = userServices.getUser(Integer.parseInt(deliveryPersonId));
        if (userDTO == null) {
            throw new ApplicationException(Message.User.NO_SUCH_DELIVERY_PERSON);
        }
        if (!Objects.equals(userDTO.getRole(), Roles.ROLE_DELIVERY_PERSON)) {
            throw new ApplicationException(Message.User.NOT_A_DELIVERY_PERSON);
        }
    }

    public static void validateGetOrdersByStatus(String orderStatus) throws ApplicationException {
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

    public static void validateGetOrdersOfUser(JPAUserDTO userDTO) throws ApplicationException, DBException {
        if (userServices.getUser(userDTO.getEmail()) == null) {
            throw new ApplicationException(Message.User.NO_SUCH_USER);
        }
    }
}
