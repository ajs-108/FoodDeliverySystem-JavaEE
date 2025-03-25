package com.app.service;

import com.app.common.enums.Roles;
import com.app.common.exception.ApplicationException;
import com.app.common.exception.DBException;
import com.app.controller.validation.SignUpValidator;
import com.app.dao.IUserDAO;
import com.app.dao.impl.UserDAOImpl;
import com.app.dto.UserDTO;
import com.app.mapper.UserMapper;

import java.util.List;
import java.util.Objects;

/**
 * User Service class contains the methods to handle the operation of database such as insert, fetch, update
 */
public class UserServices {
    private IUserDAO userDAO;
    private UserMapper userMapper;

    public UserServices() {
        userDAO = new UserDAOImpl();
        userMapper = new UserMapper();
    }

    public void addDeliveryPerson(UserDTO userDTO) throws DBException, ApplicationException {
        userDTO.setRole(Roles.ROLE_DELIVERY_PERSON);
        signUp(userDTO);
    }

    public void signUp(UserDTO userDTO) throws DBException, ApplicationException {
        SignUpValidator.validate(userDTO);
        saveUser(userDTO);
    }

    public void saveUser(UserDTO userDTO) throws DBException {
        userDAO.saveUser(userMapper.toUser(userDTO));
    }

    public UserDTO getUser(String email) throws DBException {
        return userMapper.toDTO(userDAO.getUser(email));
    }

    public List<UserDTO> getAllUsers(int roleId) throws DBException {
        return userDAO.getAllUsers(roleId)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public void updateUser(UserDTO userDTO, int userId) throws DBException {
        userDAO.updateUser(userMapper.toUser(userDTO), userId);
    }

    public boolean isEmailExists(String email, int roleId) throws DBException {
        List<UserDTO> userList = getAllUsers(roleId);
        for (UserDTO u : userList) {
            if (Objects.equals(u.getEmail(), email)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPhoneNumberExists(String phoneNumber, int roleId) throws DBException {
        List<UserDTO> userList = getAllUsers(roleId);
        for (UserDTO u : userList) {
            if (Objects.equals(u.getPhoneNumber(), phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public UserDTO getUserLoginCredentials(String email) throws DBException {
        return userMapper.toDTO(userDAO.getUserLoginCredentials(email));
    }

    public boolean isUserValid(String email) throws DBException {
        return userDAO.getUserLoginCredentials(email) != null;
    }

    public void changePassword(UserDTO userDTO) throws DBException {
        userDAO.updatePassword(userDTO.getEmail(), userDTO.getNewPassword());
    }
}
