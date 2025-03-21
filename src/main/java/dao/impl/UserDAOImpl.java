package dao.impl;

import common.Message;
import common.enums.Roles;
import common.exception.DBException;
import config.DBConnector;
import dao.IUserDAO;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {
    @Override
    public void saveUser(User user) throws DBException {
        String sql = """
                insert into user_ (first_name, last_name, email, password_, phone_number, address, role_id)
                values (?,?,?,?,?,?,?);
                """;
        try (Connection connect = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setInt(7, user.getRole().getRoleId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.GENERIC_ERROR, e);
        }
    }

    @Override
    public User getUser(String email) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        String sql = "Select * from user_ where email = ?";
        try {
            connection = DBConnector.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setAddress(resultSet.getString("address"));
                user.setRole(Roles.fromId(resultSet.getInt("role_id")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.GENERIC_ERROR, e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, resultSet, connection);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers(int roleId) throws DBException {
        Connection connection = null;
        List<User> userList = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "Select * from user_ where role_id = ?";
        try {
            connection = DBConnector.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, roleId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setAddress(resultSet.getString("address"));
                user.setCreatedOn(resultSet.getTimestamp("created_on"));
                user.setUpdatedOn(resultSet.getTimestamp("update_on"));
                for (Roles role : Roles.values()) {
                    if (roleId == role.getRoleId()) {
                        user.setRole(role);
                    }
                }
                userList.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.GENERIC_ERROR, e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, resultSet, connection);
        }
        return userList;
    }

    @Override
    public void updateUser(User user, int userID) throws DBException {
        String sql1 = """
                update user_ set first_name = ?, last_name = ?, password_ = ?, phone_number = ?, address = ?
                where user_id = ?;
                """;
        try (Connection connection = DBConnector.getInstance().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql1)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setInt(6, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.GENERIC_ERROR, e);
        }
    }

    @Override
    public User getUserLoginCredentials(String email) throws DBException {
        String sql = "select password_, email from user_ where email = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password_"));
                return user;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(Message.Error.GENERIC_ERROR, e);
        } finally {
            DBConnector.resourceCloser(preparedStatement, resultSet, connection);
        }
        return null;
    }
}
