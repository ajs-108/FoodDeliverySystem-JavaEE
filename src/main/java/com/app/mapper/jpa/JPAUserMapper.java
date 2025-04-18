package com.app.mapper.jpa;

import com.app.dto.jpa.JPAUserDTO;
import com.app.model.jpa.JPAUser;

public class JPAUserMapper {
    private UserRoleMapper userRoleMapper = new UserRoleMapper();

    public JPAUserDTO toDTO(JPAUser user) {
        JPAUserDTO userDTO = new JPAUserDTO();
        if (user == null) {
            return null;
        }
        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setAddress(user.getAddress());
        userDTO.setCreatedOn(user.getCreatedOn());
        userDTO.setUpdatedOn(user.getUpdatedOn());
        userDTO.setRole(userRoleMapper.toDTO(user.getRole()));
        userDTO.setAccountStatus(user.getAccountStatus());
        return userDTO;
    }

    public JPAUser toUser(JPAUserDTO userDTO) {
        JPAUser user = new JPAUser();
        if (userDTO == null) {
            return null;
        }
        user.setUserId(userDTO.getUserId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setCreatedOn(userDTO.getCreatedOn());
        user.setUpdatedOn(userDTO.getUpdatedOn());
        user.setRole(userRoleMapper.toRole(userDTO.getRole()));
        user.setAccountStatus(userDTO.getAccountStatus());
        return user;
    }
}
