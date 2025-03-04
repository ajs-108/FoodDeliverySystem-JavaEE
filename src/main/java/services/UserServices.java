package services;

import dbUtil.DBConnector;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UserServices {

    private Connection connect;

    public UserServices() throws SQLException {

        connect = DBConnector.getConnection();
    }

    public int insertRecord(User user) throws SQLException {

        String sql = """
                insert into user_ (first_name, last_name, email, password_, phone_number, address, role_id)
                values (?,?,?,?,?,?,?);
                """;
        PreparedStatement ps = connect.prepareStatement(sql);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getPhoneNumber());
        ps.setString(6, user.getAddress());
        ps.setInt(7, user.getRoleId());

        return ps.executeUpdate();
    }

    public ResultSet fetchRecordByEmail(String email) throws SQLException {

        PreparedStatement ps = connect.prepareStatement("Select * from user_ where email = ?;");
        ps.setString(1, email);
        return ps.executeQuery();
    }


}
