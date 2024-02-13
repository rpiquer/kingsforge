package com.kingsforge.kingsforge.persistance.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kingsforge.kingsforge.business.entity.Product;
import com.kingsforge.kingsforge.persistance.DBUtil;
import com.kingsforge.kingsforge.persistance.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {

    private JdbcTemplate jdbcTemplate;

    public ProductRepositoryImpl() {
        this.jdbcTemplate = new JdbcTemplate(DBUtil.getDataSource());
    }

    public void setDatasource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Product getProduct(int product_id, String language) throws RuntimeException {

        Connection connection = DBUtil.getConnection();

        try {
            Product product = null;
            String sql = "SELECT * FROM Product where product_id = ?";
            ResultSet resultSet = DBUtil.select(connection, sql, List.of(product_id));
            while (resultSet.next()) {
                product = new Product(resultSet.getInt("product_id"), resultSet.getInt("stock"),
                        resultSet.getBigDecimal("price"), 
                        resultSet.getString("name_"+language),
                        resultSet.getString("description_"+language), 
                        resultSet.getInt("category_id"),
                        resultSet.getInt("supplier_id"));
            }

            DBUtil.closeConnection(connection);
            if(product == null){
                throw new RuntimeException("No se ha encontrado ningún producto");
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("No se ha encontrado el producto");
        }
    }

    public List<Product> getProductsByCategory(int category_id, String language) throws RuntimeException {

        Connection connection = DBUtil.getConnection();

        try {
            List<Product> productsList = new ArrayList<Product>();
            String sql = "SELECT * FROM product where category_id = ?";
            ResultSet resultSet = DBUtil.select(connection, sql, List.of(category_id));
            while (resultSet.next()) {
                productsList.add(new Product(resultSet.getInt("product_id"), resultSet.getInt("stock"),
                        resultSet.getBigDecimal("price"), 
                        resultSet.getString("name_"+language),
                        resultSet.getString("description_"+language), 
                        resultSet.getInt("category_id"),
                        resultSet.getInt("supplier_id")));
            }
            DBUtil.closeConnection(connection);
            return productsList;
            
        } catch (SQLException e) {
            throw new RuntimeException("No se ha encontrado la categoria");
        }
    }

    public List<Product> search(String name, String language) throws RuntimeException {

        Connection connection = DBUtil.getConnection();

        try {
            List<Product> productsList = new ArrayList<Product>();
            String sql = null;
            if ("es".equals(language)) {
                sql = "SELECT * FROM product where name_es LIKE ?";
            } else {
                sql = "SELECT * FROM product where name_en LIKE ?";
            }
            
            ResultSet resultSet = DBUtil.select(connection, sql, List.of("%"+name+"%"));
            while (resultSet.next()) {
                productsList.add(new Product(resultSet.getInt("product_id"), resultSet.getInt("stock"),
                        resultSet.getBigDecimal("price"), 
                        resultSet.getString("name_"+language),
                        resultSet.getString("description_"+language), 
                        resultSet.getInt("category_id"),
                        resultSet.getInt("supplier_id")));
            }
            DBUtil.closeConnection(connection);
            return productsList;
            
        } catch (SQLException e) {
            throw new RuntimeException("No se ha encontrado ningún producto");
        }
    }

    @Override
    public List<Product> orderByStock(String language) throws RuntimeException {
        Connection connection = DBUtil.getConnection();

        try {
            List<Product> productsList = new ArrayList<Product>();
            String sql = "select * from product order by stock desc limit 5;";
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                productsList.add(new Product(resultSet.getInt("product_id"), resultSet.getInt("stock"),
                        resultSet.getBigDecimal("price"), 
                        resultSet.getString("name_"+language),
                        resultSet.getString("description_"+language), 
                        resultSet.getInt("category_id"),
                        resultSet.getInt("supplier_id")));
            }

            DBUtil.closeConnection(connection);
            return productsList;
            
        } catch (SQLException e) {
            throw new RuntimeException("No se han encontrado los más vendidos");
        }
    }

    public List<Product> searchByName(String name, String language, String order, int page) throws RuntimeException{
        List<Product> products = new ArrayList<>();
        String sql = null;
        if (language.equals("es") && order.equals("asc")) {
            sql = "SELECT * FROM product where name_es like ? order by name_es asc limit 10 offset ?";
        } else if (language.equals("es") && order.equals("desc")) {
            sql = "SELECT * FROM product where name_es like ? order by name_es desc limit 10 offset ?";
        } else if (language.equals("en") && order.equals("asc")) {
            sql = "SELECT * FROM product where name_en like ? order by name_en asc limit 10 offset ?";
        } else if (language.equals("en") && order.equals("desc")) {
            sql = "SELECT * FROM product where name_en like ? order by name_en desc limit 10 offset ?";
        }
        int offset = (page-1)*10;
        ResultSet resultSet = DBUtil.select(DBUtil.getConnection(), sql, List.of("%"+name+"%", offset));
        try {
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("product_id"), resultSet.getInt("stock"),
                        resultSet.getBigDecimal("price"), 
                        resultSet.getString("name_"+language),
                        resultSet.getString("description_"+language), 
                        resultSet.getInt("category_id"),
                        resultSet.getInt("supplier_id")));
            }

            if(products.get(0) == null){
                throw new RuntimeException("No se ha encontrtado ningun producto de esa categoria");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Resource not found");
        }
        return products;
    }

    public List<Product> searchByPrice(String name, String language, String order, int page) throws RuntimeException{
        List<Product> products = new ArrayList<>();
        String sql = null;
        if (language.equals("es") && order.equals("asc")) {
            sql = "SELECT * FROM product where name_es like ? order by price asc limit 10 offset ?";
        } else if (language.equals("es") && order.equals("desc")) {
            sql = "SELECT * FROM product where name_es like ? order by price desc limit 10 offset ?";
        } else if (language.equals("en") && order.equals("asc")) {
            sql = "SELECT * FROM product where name_en like ? order by price asc limit 10 offset ?";
        } else if (language.equals("en") && order.equals("desc")) {
            sql = "SELECT * FROM product where name_en like ? order by price desc limit 10 offset ?";
        }
        int offset = (page-1)*10;
        ResultSet resultSet = DBUtil.select(DBUtil.getConnection(), sql, List.of("%"+name+"%", offset));
        try {
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("product_id"), resultSet.getInt("stock"),
                        resultSet.getBigDecimal("price"), 
                        resultSet.getString("name_"+language),
                        resultSet.getString("description_"+language), 
                        resultSet.getInt("category_id"),
                        resultSet.getInt("supplier_id")));
            }

            if(products.get(0) == null){
                throw new RuntimeException("No se ha encontrtado ningun producto de esa categoria");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Resource not found");
        }
        return products;
    }
}
