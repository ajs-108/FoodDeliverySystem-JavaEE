package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.ICartRepository;
import com.app.dao.jpa.impl.CartRepository;
import com.app.dto.jpa.JPACartDTO;
import com.app.dto.jpa.JPAUserDTO;
import com.app.mapper.jpa.JPACartMapper;
import com.app.mapper.jpa.JPAUserMapper;
import jakarta.transaction.Transactional;

import java.util.List;

public class JPACartServices {
    private ICartRepository cartRepo;
    private JPACartMapper cartMapper;
    private JPAUserMapper userMapper;

    public JPACartServices() {
        cartRepo = new CartRepository();
        cartMapper = new JPACartMapper();
        userMapper = new JPAUserMapper();
    }

    @Transactional
    public void addFoodItem(JPACartDTO cartDTO) throws DBException {
        cartRepo.save(cartMapper.toCart(cartDTO));
    }

    public List<JPACartDTO> find(int userId) throws DBException {
        List<JPACartDTO> cartDTOList = cartRepo.find(userId)
                .stream()
                .map(cartMapper::toDTO)
                .toList();
        if (cartDTOList != null) {
            double totalPrice = 0;
            for (JPACartDTO cartDTO : cartDTOList) {
                cartDTO.setBeforeDiscountPrice(
                        calculatePreDiscountPrice(
                                cartDTO.getFoodItem().getPrice(),
                                cartDTO.getQuantity()
                        ));
                cartDTO.setAfterDiscountPrice(
                        calculatePostDiscountPrice(
                                cartDTO.getFoodItem().getPrice(),
                                cartDTO.getFoodItem().getDiscount(),
                                cartDTO.getQuantity()
                        ));
                totalPrice += calculateTotalPrice(cartDTO.getAfterDiscountPrice());
            }
            for (JPACartDTO cartDTO : cartDTOList) {
                cartDTO.setTotalPrice(totalPrice);
            }
        }
        return cartDTOList;
    }

    public void removeFoodItem(JPACartDTO cartDTO) throws DBException {
        cartRepo.remove(cartMapper.toCart(cartDTO));
    }

    public void updateQuantity(JPACartDTO cartDTO) throws DBException {
        cartRepo.updateQuantity(cartMapper.toCart(cartDTO));
    }

    public void removeCartOfUser(JPAUserDTO userDTO) throws DBException {
        cartRepo.removeCartOfUser(userMapper.toUser(userDTO));
    }

    public double calculateTotalPrice(double price, double discount, int quantity) {
        double totalPrice = 0;
        totalPrice += calculatePostDiscountPrice(price, discount, quantity);
        return Math.round(totalPrice);
    }

    public double calculateTotalPrice(double discountedPrice) {
        double totalPrice = 0;
        totalPrice += discountedPrice;
        return Math.round(totalPrice);
    }

    public double calculatePreDiscountPrice(double price, int quantity) {
        return Math.round(price * quantity);
    }

    public double calculatePostDiscountPrice(double price, double discount, int quantity) {
        return Math.round((price - (price * (discount / 100))) * quantity);
    }
}
