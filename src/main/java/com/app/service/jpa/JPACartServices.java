package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.ICartRepository;
import com.app.dao.jpa.impl.CartRepository;
import com.app.dto.jpa.JPACartDTO;
import com.app.mapper.jpa.JPACartMapper;
import com.app.model.jpa.JPACart;
import com.app.model.jpa.JPAUser;
import com.app.service.ShoppingCartServices;

import java.util.List;

public class JPACartServices {
    private ICartRepository cartRepo;
    private JPACartMapper cartMapper;
    private ShoppingCartServices cartServices;

    public JPACartServices() {
        cartRepo = new CartRepository();
        cartMapper = new JPACartMapper();
        cartServices = new ShoppingCartServices();
    }

    void save(JPACart cart) throws DBException {
        cartRepo.save(cart);
    }

    public List<JPACartDTO> find(int userId) throws DBException {
        List<JPACartDTO> cartDTOList = cartRepo.find(userId)
                .stream()
                .map(cartMapper::toDTO)
                .toList();
        if (cartDTOList != null) {
            double totalPrice = 0;
            for(JPACartDTO cartDTO : cartDTOList) {
                cartDTO.setBeforeDiscountPrice(
                        cartServices.calculatePreDiscountPrice(
                                cartDTO.getFoodItem().getPrice(),
                                cartDTO.getQuantity()
                        ));
                cartDTO.setAfterDiscountPrice(
                        cartServices.calculatePostDiscountPrice(
                                cartDTO.getFoodItem().getPrice(),
                                cartDTO.getFoodItem().getDiscount(),
                                cartDTO.getQuantity()
                        ));
                totalPrice += cartServices.calculateTotalPrice(cartDTO.getAfterDiscountPrice());
            }
            for (JPACartDTO cartDTO : cartDTOList) {
                cartDTO.setTotalPrice(totalPrice);
            }
        }
        return cartDTOList;
    }

    void remove(JPACart cart) throws DBException {
        cartRepo.remove(cart);
    }

    void updateQuantity(JPACart cart) throws DBException {
        cartRepo.updateQuantity(cart);
    }

    void removeCartOfUser(JPAUser user) throws DBException {
        cartRepo.removeCartOfUser(user);
    }
}
