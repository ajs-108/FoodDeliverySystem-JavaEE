package services;

import common.APIResponse;
import dao.UserDAOImpl;
import model.User;
import util.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * User Service class contains the methods to handle the operation of database such as insert, fetch, update
 */
public class UserServices {

    UserDAOImpl userDAO;

    public UserServices() {
        this.userDAO = new UserDAOImpl();
    }

    public void saveUser(User user) throws SQLException {
        userDAO.saveUser(user);
    }

    /**
     * This method is used for retrieving the data of user by email address of the user
     * @param email - User email address to retrieve data
     */
    public User getUser(String email) throws SQLException {
       return userDAO.getUser(email);
    }

    public List<User> getAllUsers(int role_id) throws SQLException {
       return userDAO.getAllUsers(role_id);
    }

    public void modifyUser(User user) throws SQLException {
        userDAO.updateUser(user);
    }
}
