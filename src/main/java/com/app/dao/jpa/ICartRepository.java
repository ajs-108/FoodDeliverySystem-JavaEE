package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.model.jpa.JPACart;
import com.app.model.jpa.JPAUser;

import java.util.List;

public interface ICartRepository {
    void save(JPACart cart) throws DBException;

    List<JPACart> find(int userId) throws DBException;

    void remove(JPACart cart) throws DBException;

    void updateQuantity(JPACart cart) throws DBException;

    void removeCartOfUser(JPAUser user) throws DBException;
}
