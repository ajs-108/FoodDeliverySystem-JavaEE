package com.app.service.jpa;

import com.app.common.enums.OrderStatus;
import com.app.common.exception.DBException;
import com.app.dao.jpa.IOrderFoodItemRepository;
import com.app.dao.jpa.IOrderRepository;
import com.app.dao.jpa.impl.OrderFoodItemsRepository;
import com.app.dao.jpa.impl.OrderRepository;
import com.app.dto.jpa.JPACartDTO;
import com.app.dto.jpa.order.GetOrderDTO;
import com.app.dto.jpa.order.JPAOrderDTO;
import com.app.mapper.FoodItemMapper;
import com.app.mapper.jpa.JPAOrderMapper;
import com.app.model.FoodItem;
import com.app.model.jpa.JPAOrderFoodItems;
import com.app.model.jpa.JPAUser;

import java.util.List;

public class JPAOrderServices {
    private IOrderRepository orderRepo;
    private IOrderFoodItemRepository orderFoodItemRepo;
    private JPAOrderMapper orderMapper;
    private FoodItemMapper foodItemMapper;

    public JPAOrderServices() {
        orderRepo = new OrderRepository();
        orderFoodItemRepo = new OrderFoodItemsRepository();
        orderMapper = new JPAOrderMapper();
        foodItemMapper = new FoodItemMapper();
    }

    public void save(JPAOrderDTO orderDTO, JPACartDTO cartDTO) throws DBException {
        orderRepo.save(orderMapper.toOrder(orderDTO));
        JPAOrderFoodItems orderFoodItems = new JPAOrderFoodItems();
        orderFoodItems.setOrder(orderMapper.toOrder(orderDTO));
        orderFoodItems.setFoodItem(foodItemMapper.toFoodItem(cartDTO.getFoodItem()));
        orderFoodItems.setQuantity(cartDTO.getQuantity());
        orderFoodItems.setFoodItemsTotal(calculateFoodItemsTotal(
                foodItemMapper.toFoodItem(cartDTO.getFoodItem()),
                cartDTO.getQuantity()));
        orderFoodItemRepo.save(orderFoodItems);
    }

    public List<GetOrderDTO> findAll() throws DBException {
        List<GetOrderDTO> orderList = orderRepo.findAll();
        for (GetOrderDTO order : orderList) {
            order.setOrderFoodItems(
                    orderFoodItemRepo.findByOrder(order.getOrderId()));
        }
        return orderList;
    }

    public List<GetOrderDTO> findAll(OrderStatus orderStatus) throws DBException {
        return orderRepo.findAll(orderStatus);
    }

    public List<GetOrderDTO> findAllPreviousOrdersOfUser(int userId, int roleId) throws DBException {
        return orderRepo.findAllPreviousOrdersOfUser(userId, roleId);
    }

    public GetOrderDTO findById(int orderId) throws DBException {
        return orderRepo.findById(orderId);
    }

    public GetOrderDTO findRecentOrderOfUser(int userId) throws DBException {
        return orderRepo.findRecentOrderOfUser(userId);
    }

    public List<GetOrderDTO> findOrderAssignedToDP(int deliveryPersonId) throws DBException {
        return orderRepo.findOrdersAssignedToDP(deliveryPersonId);
    }

    public List<GetOrderDTO> findCurrentOrderOfUser(int userId) throws DBException {
        return orderRepo.findCurrentOrdersOfUser(userId);
    }

    public void updateOrderStatus(int orderId, OrderStatus orderStatus) throws DBException {
        orderRepo.updateStatus(orderId, orderStatus);
    }

    public void assignDeliveryPerson(int orderId, int deliveryPersonId) throws DBException {
        JPAUser deliveryPerson = new JPAUser();
        deliveryPerson.setUserId(deliveryPersonId);
        orderRepo.assignDeliveryPerson(orderId, deliveryPerson);
    }

    public Double calculateFoodItemsTotal(FoodItem foodItem, short quantity) throws DBException {
        return foodItem.getPrice()-(foodItem.getPrice()*(foodItem.getDiscount()/100))*quantity;
    }
}
