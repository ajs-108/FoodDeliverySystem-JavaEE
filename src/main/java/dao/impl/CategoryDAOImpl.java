package dao.impl;

import dao.ICategoryDAO;
import model.Category;
import common.config.DBConnector;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CategoryDAOImpl implements ICategoryDAO {
    @Override
    public void saveCategory(Category category) throws SQLException {
        Connection connect = null;
        PreparedStatement ps = null;
        String sql = "insert into category(category_name) values (?)";
        try {
            connect = DBConnector.getInstance().getConnection();
            ps = connect.prepareStatement(sql);
            ps.setString(1, category.getCategoryName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
    }

    @Override
    public Category getCategory(int category_id) throws SQLException {
        Connection connect = null;
        Category category = new Category();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from category where category_id = ?";
        try {
            connect = DBConnector.getInstance().getConnection();
            ps = connect.prepareStatement(sql);
            ps.setInt(1, category_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
        return category;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        Connection connect = null;
        List<Category> categoryList = new LinkedList<>();
        Category category;
        Statement s = null;
        ResultSet rs = null;
        String sql = "Select * from category";
        try {
            connect = DBConnector.getInstance().getConnection();
            s = connect.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                categoryList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                s.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.close();
            }
        }
        return categoryList;
    }
}
