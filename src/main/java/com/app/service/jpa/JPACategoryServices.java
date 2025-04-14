package com.app.service.jpa;

import com.app.common.exception.DBException;
import com.app.dao.jpa.IJPACategoryDAO;
import com.app.dao.jpa.impl.JPACategoryDAO;
import com.app.model.Category;

import java.util.List;

public class JPACategoryServices {
    private IJPACategoryDAO jpaCategoryDAO;

    public JPACategoryServices() {
        jpaCategoryDAO = new JPACategoryDAO();
    }

    public List<Category> findAll() throws DBException {
        return jpaCategoryDAO.findAll();
    }
}
