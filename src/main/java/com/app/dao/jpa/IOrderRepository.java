package com.app.dao.jpa;

import com.app.common.enums.OrderStatus;
import com.app.common.exception.DBException;
import com.app.dto.jpa.order.GetOrderDTO;
import com.app.model.jpa.JPAOrder;
import com.app.model.jpa.JPAUser;

import java.util.List;

public interface IOrderRepository {
    Integer save(JPAOrder order) throws DBException;

    List<GetOrderDTO> findAll() throws DBException;

    List<GetOrderDTO> findAll(OrderStatus orderStatus) throws DBException;

    List<GetOrderDTO> findAllPreviousOrdersOfUser(int userId, int roleId) throws DBException;

    JPAOrder find(int orderId) throws DBException;

    GetOrderDTO findById(int orderId) throws DBException;

    GetOrderDTO findRecentOrderOfUser(int userId) throws DBException;

    List<GetOrderDTO> findOrdersAssignedToDP(int deliveryPersonId) throws DBException;

    List<GetOrderDTO> findCurrentOrdersOfUser(int userId) throws DBException;

    void updateStatus(int orderId, OrderStatus orderStatus) throws DBException;

    void assignDeliveryPerson(int orderId, JPAUser deliveryPerson) throws DBException;
}
