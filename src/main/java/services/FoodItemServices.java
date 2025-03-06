package services;

import entity.FoodItem;
import util.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FoodItemServices {

    Connection connect;

    public FoodItemServices() throws SQLException {

        this.connect = DBConnector.getConnection();
    }

    /**
     * Insert Record method is used for inserting the FoodItem records in the Database
     *
     * @param foodItem used to take the User data.
     */
    public int insertRecord(FoodItem foodItem) throws SQLException {

        String sql = """
                insert into food_item(food_item_id, food_name, food_description, price, gram, size, discount, is_available, category_id, image_path, rating)
                values (?,?,?,?,?,?,?,?,?,?,?);
                """;
        PreparedStatement ps = connect.prepareStatement(sql);


        return ps.executeUpdate();
    }
}
