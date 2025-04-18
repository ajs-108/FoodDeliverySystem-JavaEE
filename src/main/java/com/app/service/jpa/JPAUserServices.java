package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.IUserRepository;
import com.app.dao.jpa.impl.UserRepository;
import com.app.dto.jpa.JPAUserDTO;
import com.app.mapper.jpa.JPAUserMapper;

import java.util.List;

public class JPAUserServices {
    private IUserRepository userRepo;
    private JPAUserMapper userMapper;

    public JPAUserServices() {
        userRepo = new UserRepository();
        userMapper = new JPAUserMapper();
    }

    public List<JPAUserDTO> findAll(int roleId) throws DBException {
        return userRepo.findAll(roleId)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
