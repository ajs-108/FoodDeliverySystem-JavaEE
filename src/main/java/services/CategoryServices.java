package services;

import entity.Category;
import util.DBConnector;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CategoryServices {

    /**
     * Insert Record method is used for inserting the Food Category records in the Database
     *
     * @param category used to take the User data.
     */
    public int saveCategory(Category category) throws SQLException {
        Connection connect = DBConnector.getConnection();
        PreparedStatement ps = null;
        String sql = "insert into category(category_name) values (?)";
        int flag;
        try {
            ps = connect.prepareStatement(sql);
            ps.setString(1, category.getCategoryName());
            flag = ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            DBConnector.closeConnection();
        }
        return flag;
    }

    public List<Category> getAllCategories() throws SQLException {
        Connection connect = DBConnector.getConnection();
        List<Category> categoryList = new LinkedList<>();
        Category category;
        Statement s = null;
        ResultSet rs = null;
        String sql = "Select * from category";
        try {
            s = connect.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                categoryList.add(category);
            }
        } finally {
            if (s != null) {
                s.close();
            }
            if (rs != null) {
                rs.close();
            }
            DBConnector.closeConnection();
        }
        return categoryList;
    }

    public Category getCategory(int category_id) throws SQLException {
        Connection connect = DBConnector.getConnection();
        Category category = new Category();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "Select * from category where category_id = ?";
        try {
            ps = connect.prepareStatement(sql);
            ps.setInt(1, category_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            DBConnector.closeConnection();
        }
        return category;
    }
}
