package com.app.dao.jpa;

import com.app.common.exception.DBException;
import com.app.model.FoodItem;

import java.util.List;

public interface IJPAFoodItemDAO {
    List<FoodItem> findAll() throws DBException;
}
