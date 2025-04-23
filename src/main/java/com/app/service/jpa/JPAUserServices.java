package com.app.service.jpa;

import com.app.common.enums.AccountStatus;
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

    public void save(JPAUserDTO userDTO) throws DBException {
        userRepo.save(userMapper.toUser(userDTO));
    }

    public List<JPAUserDTO> findAll(int roleId) throws DBException {
        return userRepo.findAll(roleId)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public JPAUserDTO find(int userId) throws DBException {
        return userMapper.toDTO(userRepo.find(userId));
    }

    public JPAUserDTO find(String email) throws DBException {
        return userMapper.toDTO(userRepo.find(email));
    }

    public JPAUserDTO findLoginCredentials(String email) throws DBException {
        return userMapper.toDTO(userRepo.findLoginCredentials(email));
    }

    public void update(int userId, JPAUserDTO userDTO) throws DBException {
        userRepo.update(userId, userMapper.toUser(userDTO));
    }

    public void updatePassword(String email, String newPassword) throws DBException {
        userRepo.updatePassword(email, newPassword);
    }

    public void updateAccountStatus(int userId, AccountStatus accountStatus) throws DBException {
        userRepo.updateAccountStatus(userId, accountStatus);
    }
}
