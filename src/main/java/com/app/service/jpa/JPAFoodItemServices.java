package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.IFoodItemDAO;
import com.app.dao.impl.FoodItemDAOImpl;
import com.app.dao.jpa.IJPACategoryDAO;
import com.app.dao.jpa.IJPAFoodItemDAO;
import com.app.dao.jpa.impl.JPACategoryDAO;
import com.app.dao.jpa.impl.JPAFoodItemDAO;
import com.app.dto.FoodItemDTO;
import com.app.mapper.FoodItemMapper;
import com.app.model.Category;
import com.app.model.FoodItem;

import java.util.List;

public class JPAFoodItemServices {
    private IJPAFoodItemDAO jpaFoodItemDAO;

    public JPAFoodItemServices() {
        jpaFoodItemDAO = new JPAFoodItemDAO();
    }

    public List<FoodItem> findAll() throws DBException {
        return jpaFoodItemDAO.findAll();
    }
}
