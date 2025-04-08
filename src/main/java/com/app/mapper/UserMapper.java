package com.app.mapper;

import com.app.dto.UserDTO;
import com.app.model.User;

public class UserMapper {
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
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
        userDTO.setRole(user.getRole());
        userDTO.setAccountStatus(user.getAccountStatus());
        return userDTO;
    }

    public User toUser(UserDTO userDTO) {
        User user = new User();
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
        user.setRole(userDTO.getRole());
        user.setAccountStatus(userDTO.getAccountStatus());
        return user;
    }
}