package service;

import dao.IUserDAO;
import dao.impl.UserDAOImpl;
import model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * User Service class contains the methods to handle the operation of database such as insert, fetch, update
 */
public class UserServices {
    private IUserDAO userDAO;

    public UserServices() {
        this.userDAO = new UserDAOImpl();
    }

    public void saveUser(User user) throws SQLException {
        userDAO.saveUser(user);
    }

    public User getUser(String email) throws SQLException {
        return userDAO.getUser(email);
    }

    public List<User> getAllUsers(int role_id) throws SQLException {
        return userDAO.getAllUsers(role_id);
    }

    public void updateUser(User user, int userId) throws SQLException {
        userDAO.updateUser(user, userId);
    }
}
