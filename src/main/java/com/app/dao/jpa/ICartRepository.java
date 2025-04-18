package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.model.jpa.JPACart;

import java.util.List;

public interface ICartRepository {
    List<JPACart> find(int userId) throws DBException;
}
