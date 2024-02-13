package com.kingsforge.kingsforge.persistance.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.kingsforge.kingsforge.business.entity.Category;
import com.kingsforge.kingsforge.persistance.CategoryRepository;
import com.kingsforge.kingsforge.persistance.DBUtil;

public class CategoryRepositoryImpl implements CategoryRepository{

    private JdbcTemplate jdbcTemplate;


    public CategoryRepositoryImpl() {
        this.jdbcTemplate = new JdbcTemplate(DBUtil.getDataSource());
    }
    public void setDatasource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<Category> listAllCategories(String language){
        Connection connection = DBUtil.getConnection();

        try {  
        List<Category> categoriesList = new ArrayList<Category>();
        String sql = "SELECT * FROM Category where category_id != 0;";
        ResultSet resultSet = DBUtil.select(connection, sql, null);
        while (resultSet.next()) {
            categoriesList.add(new Category(resultSet.getInt("category_id"), 
            resultSet.getInt("father_id"), 
            resultSet.getString("name_"+language)));
        }

        DBUtil.closeConnection(connection);
        return categoriesList;
        
        } catch (SQLException e) {
            throw new RuntimeException("No hay subcategorias de esa categoria");
        }
    }

    public Category getCategoryById(int id, String language) throws RuntimeException{
        Connection connection = DBUtil.getConnection();
        try {  
        Category category = null;
        String sql = "SELECT * FROM Category where category_id = ?";
        ResultSet resultSet = DBUtil.select(connection, sql, List.of(id));
        if (resultSet.next()) {
            category = new Category(resultSet.getInt("category_id"), resultSet.getInt("father_id"), resultSet.getString("name_" +language));
        }
        DBUtil.closeConnection(connection);
        if (category==null) {
            throw new RuntimeException("No se ha encontrado la categoría");
        }
        return category;
        
        } catch (SQLException e) {
            throw new RuntimeException("No existe esta categoría");
        }
    }
    
}
