package service;

import common.exception.DBException;
import dao.IFoodItemDAO;
import dao.impl.FoodItemDAOImpl;
import model.FoodItem;

import java.util.List;

public class FoodItemServices {
    private IFoodItemDAO foodItemDAO;

    public FoodItemServices() {
        this.foodItemDAO = new FoodItemDAOImpl();
    }

    public void createFoodItem(FoodItem foodItem) throws DBException {
        foodItemDAO.saveFoodItem(foodItem);
    }

    public List<FoodItem> getAllFoodItem() throws DBException {
        return foodItemDAO.getAllFoodItems();
    }

    public FoodItem getFoodItem(int foodItemId) throws DBException {
        return foodItemDAO.getFoodItem(foodItemId);
    }

    public void updateFoodItem(FoodItem foodItem, int foodItemId) throws DBException {
        foodItemDAO.updateFoodItem(foodItem, foodItemId);
    }
}
