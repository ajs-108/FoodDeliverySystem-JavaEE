package com.app.dao;

import com.app.common.exception.DBException;
import com.app.model.User;

import java.util.List;

public interface IUserDAO {
    void saveUser(User user) throws DBException;

    User getUser(String email) throws DBException;

    List<User> getAllUsers(int roleId) throws DBException;

    void updateUser(User user, int userID) throws DBException;

    User getUserLoginCredentials(String email) throws DBException;

    void updatePassword(String email, String newPassword) throws DBException;
}
