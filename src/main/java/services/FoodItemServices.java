package services;

import util.DBConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class FoodItemServices {

    Connection connect;

    public FoodItemServices() throws SQLException {

        this.connect = DBConnector.getConnection();
    }


}
