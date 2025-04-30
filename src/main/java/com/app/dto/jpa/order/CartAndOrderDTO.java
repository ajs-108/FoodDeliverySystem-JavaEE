package com.app.dto.jpa.order;

import com.app.dto.jpa.JPACartDTO;

public class CartAndOrderDTO {
    private JPACartDTO cart;
    private JPAOrderDTO order;

    public JPACartDTO getCart() {
        return cart;
    }

    public void setCart(JPACartDTO cart) {
        this.cart = cart;
    }

    public JPAOrderDTO getOrder() {
        return order;
    }

    public void setOrder(JPAOrderDTO order) {
        this.order = order;
    }
}
