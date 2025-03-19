package service;

import common.exception.ApplicationException;
import common.util.ObjectMapperUtil;
import controller.validation.SignUpValidator;
import dto.APIResponse;
import jakarta.servlet.http.HttpServletResponse;
import mapper.UserMapper;
import common.exception.DBException;
import dao.IUserDAO;
import dao.impl.UserDAOImpl;
import dto.user_dto.UserSignUpDTO;
import model.User;

import java.io.IOException;
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

    public void saveUser(UserSignUpDTO userDTO) throws DBException {
        userDAO.saveUser(userMapper.toUser(userDTO));
    }

    public User getUser(String email) throws DBException {
        return userDAO.getUser(email);
    }

    public List<User> getAllUsers(int role_id) throws DBException {
        return userDAO.getAllUsers(role_id);
    }

    public void updateUser(UserSignUpDTO userDTO, int userId) throws DBException {
        userDAO.updateUser(userMapper.toUser(userDTO), userId);
    }

    public boolean isEmailExists(String email, int roleId) throws DBException {
        return userDAO.isEmailExists(email, roleId);
    }

    public boolean isPhoneNumberExists(String phoneNumber, int roleId) throws DBException {
        return userDAO.isPhoneNumberExists(phoneNumber, roleId);
    }

    public static void signUp(UserSignUpDTO userDTO) throws DBException, ApplicationException {
        UserServices userServices = new UserServices();
        SignUpValidator.validate(userDTO);
        userServices.saveUser(userDTO);
    }


}
