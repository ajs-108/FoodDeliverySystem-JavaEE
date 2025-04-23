package com.app.dao.jpa;

import com.app.common.enums.AccountStatus;
import com.app.common.exception.DBException;
import com.app.model.jpa.JPAUser;

import java.util.List;

public interface IUserRepository {
    void save(JPAUser user) throws DBException;

    List<JPAUser> findAll(int roleId) throws DBException;

    JPAUser find(int userId) throws DBException;

    JPAUser find(String email) throws DBException;

    JPAUser findLoginCredentials(String email) throws DBException;

    void update(int userId, JPAUser user) throws DBException;

    void updatePassword(String email, String newPassword) throws DBException;

    void updateAccountStatus(int userId, AccountStatus accountStatus) throws DBException;
}
