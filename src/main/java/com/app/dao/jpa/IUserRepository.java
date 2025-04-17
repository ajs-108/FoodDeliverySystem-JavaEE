package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.model.jpa.JPAUser;

import java.util.List;

public interface IUserRepository {
    List<JPAUser> findAll(int roleId) throws DBException;
}
