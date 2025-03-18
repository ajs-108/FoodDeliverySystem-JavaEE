package service;

import dao.impl.FoodItemDAOImpl;
import dao.IFoodItemDAO;
import model.FoodItem;

import java.sql.SQLException;
import java.util.List;

public class FoodItemServices {
    private IFoodItemDAO foodItemDAO;

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

    public void updateFoodItem(FoodItem foodItem, int foodItemId) throws SQLException {
        foodItemDAO.updateFoodItem(foodItem, foodItemId);
    }
}
