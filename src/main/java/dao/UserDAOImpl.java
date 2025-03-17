package dao;

import model.User;
import util.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {
    @Override
    public void saveUser(User user) throws SQLException {
        Connection connect = null;
        PreparedStatement ps = null;
        String sql = """
                insert into user_ (first_name, last_name, email, password_, phone_number, address, role_id)
                values (?,?,?,?,?,?,?);
                """;
        try {
            connect = DBConnector.getInstance().getConnection();
            ps = connect.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getPhoneNumber());
            ps.setString(6, user.getAddress());
            ps.setInt(7, user.getRole().getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
    }

    @Override
    public User getUser(String email) throws SQLException {
        Connection connect = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        String sql = "Select * from user_ where email = ?";
        try {
            connect = DBConnector.getInstance().getConnection();
            ps = connect.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password_"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAddress(rs.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
        return user;
    }

    @Override
    public List<User> getAllUsers(int role_id) throws SQLException {
        Connection connect = null;
        List<User> userList = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from user_ where role_id = ?";
        try {
            connect = DBConnector.getInstance().getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, role_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setFirstName(rs.getString("last_name"));
                user.setFirstName(rs.getString("email"));
                user.setFirstName(rs.getString("phone_number"));
                user.setFirstName(rs.getString("address"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
        return userList;
    }

    @Override
    public void updateUser(User user, int userID) throws SQLException {
        Connection connect = null;
        PreparedStatement ps = null;
        String sql1 = """
                update user_ set first_name = ?, last_name = ?, password_ = ?, phone_number = ?, address = ?
                where user_id = ?;
                """;
        try {
            connect = DBConnector.getInstance().getConnection();
            ps = connect.prepareStatement(sql1);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getAddress());
            ps.setInt(6, userID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
    }
}
