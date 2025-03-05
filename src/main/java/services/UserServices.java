package services;

import util.DBConnector;
import entity.User;

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

    private Connection connect;
    private List<User> userList;

    public UserServices() throws SQLException {

        connect = DBConnector.getConnection();
        userList = new LinkedList<>();
    }

    /**
     * Insert Record method is used for inserting the User records in the Database
     *
     * @param user used to take the User data.
     */
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

    /**
     * This method is used for retrieving the records by email address of the user
     * @param email
     */
    public ResultSet fetchRecordByEmail(String email) throws SQLException {

        String sql = "Select * from user_ where email = ?;";
        PreparedStatement ps = connect.prepareStatement(sql);
        ps.setString(1, email);
        return ps.executeQuery();
    }

    public List<User> fetchAllRecords(int role_id) throws SQLException {

        String sql = "Select * from user_ where role_id = ?;";
        PreparedStatement ps = connect.prepareStatement(sql);
        ps.setInt(1, role_id);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            userList.add(
                    new User(
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            null,
                            rs.getString("phone_number"),
                            rs.getString("address"),
                            rs.getInt("role_id")
                    )
            );
        }
        return userList;
    }

}
