package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    void saveUser(User user) throws SQLException;
    User getUser(String email) throws SQLException;
    List<User> getAllUsers(int role_id) throws SQLException;
    void updateUser(User user, int userID) throws SQLException;
}
