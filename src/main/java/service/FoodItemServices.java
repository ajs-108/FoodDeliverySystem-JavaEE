package services;

import dao.FoodItemDAOImpl;
import model.FoodItem;

import java.sql.SQLException;
import java.util.List;

public class FoodItemServices {
    private FoodItemDAOImpl foodItemDAO;

    public FoodItemServices() {
        this.foodItemDAO = new FoodItemDAOImpl();
    }

    public void createFoodItem(FoodItem foodItem) throws SQLException {
        foodItemDAO.saveFoodItem(foodItem);
    }

    public List<FoodItem> getAllFoodItem() throws SQLException {
        return foodItemDAO.getAllFoodItems();
    }

    public FoodItem getFoodItem(int foodItemId) throws SQLException {
        return foodItemDAO.getFoodItem(foodItemId);
    }
}
