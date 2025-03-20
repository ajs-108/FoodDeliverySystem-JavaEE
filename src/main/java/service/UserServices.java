package service;

import common.exception.ApplicationException;
import controller.validation.SignUpValidator;
import dto.UserDTO;
import mapper.UserMapper;
import common.exception.DBException;
import dao.IUserDAO;
import dao.impl.UserDAOImpl;
import model.User;

import java.util.List;

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

    public void saveUser(UserDTO userDTO) throws DBException {
        userDAO.saveUser(userMapper.toUser(userDTO));
    }

    public User getUser(String email) throws DBException {
        return userDAO.getUser(email);
    }

    public List<User> getAllUsers(int roleId) throws DBException {
        return userDAO.getAllUsers(roleId);
    }

    public void updateUser(UserDTO userDTO, int userId) throws DBException {
        userDAO.updateUser(userMapper.toUser(userDTO), userId);
    }

    public boolean isEmailExists(String email, int roleId) throws DBException {
        return userDAO.isEmailExists(email, roleId);
    }

    public boolean isPhoneNumberExists(String phoneNumber, int roleId) throws DBException {
        return userDAO.isPhoneNumberExists(phoneNumber, roleId);
    }

    public static void signUp(UserDTO userDTO) throws DBException, ApplicationException {
        UserServices userServices = new UserServices();
        SignUpValidator.validate(userDTO);
        userServices.saveUser(userDTO);
    }


}
