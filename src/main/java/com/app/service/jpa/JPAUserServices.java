package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.IUserRepository;
import com.app.dao.jpa.impl.UserRepository;
import com.app.model.jpa.JPAUser;

import java.util.List;

public class JPAUserServices {
    private IUserRepository userRepo;

    public JPAUserServices() {
        userRepo = new UserRepository();
    }

    public List<JPAUser> findAll(int roleId) throws DBException {
        return userRepo.findAll(roleId);
    }
}
