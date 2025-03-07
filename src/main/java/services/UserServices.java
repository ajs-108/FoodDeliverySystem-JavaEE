package services;

import common.APIResponse;
import entity.User;
import jakarta.servlet.http.HttpServletResponse;
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

    /**
     * Insert Record method is used for saving the data of User in the Database
     * @param user User object used to take data of User.
     */
    public APIResponse saveUser(User user) throws SQLException {
        Connection connect = DBConnector.getConnection();
        APIResponse apiResponse = new APIResponse();
        PreparedStatement ps = null;
        String sql = """
                insert into user_ (first_name, last_name, email, password_, phone_number, address, role_id)
                values (?,?,?,?,?,?,?);
                """;
        int flag;
        try {
            ps = connect.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getPhoneNumber());
            ps.setString(6, user.getAddress());
            ps.setInt(7, user.getRole().getId());
            flag = ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            DBConnector.closeConnection();
        }
        if (flag == 1) {
            apiResponse.setMessage("Account Created Successfully");
        }
        return apiResponse;
    }

    /**
     * This method is used for retrieving the data of user by email address of the user
     * @param email - User email address to retrieve data
     */
    public User getUser(String email) throws SQLException {
        Connection connect = DBConnector.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        String sql = "Select * from user_ where email = ?";
        try {
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
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            DBConnector.closeConnection();
        }
        return user;
    }

    public List<User> getAllUsers(int role_id) throws SQLException {
        List<User> userList = new LinkedList<>();
        Connection connect = DBConnector.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from user_ where role_id = ?";
        try {
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
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            DBConnector.closeConnection();
        }
        return userList;
    }

    public int updateUser(User user) throws SQLException {
        Connection connect = DBConnector.getConnection();
        PreparedStatement ps = null;
        String sql1 = """
                update user_ set first_name = ?, last_name = ?, password_ = ?, phone_number = ?, address = ?
                where user_id = ?;
                """;
        int i;
        try {
            ps = connect.prepareStatement(sql1);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getAddress());
            ps.setInt(6, user.getUserId());
            i = ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            DBConnector.closeConnection();
        }
        return i;
    }
}
