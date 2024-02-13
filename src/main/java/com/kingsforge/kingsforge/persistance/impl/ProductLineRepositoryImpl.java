package com.kingsforge.kingsforge.persistance.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingsforge.kingsforge.business.entity.Product;
import com.kingsforge.kingsforge.business.entity.ProductLine;
import com.kingsforge.kingsforge.persistance.DBUtil;
import com.kingsforge.kingsforge.persistance.ProductLineRepository;

public class ProductLineRepositoryImpl implements ProductLineRepository {

    public void insertProductLine(ProductLine productLine, int cart_id) throws SQLException {

        Connection connection = DBUtil.getConnection();
        System.out.println(productLine);
        System.out.println(cart_id);
        String sql = "INSERT INTO product_line (quantity, unitPrice, product_id, cart_id) VALUES (?, ?, ?, ?)";
        DBUtil.insert(connection, sql,
                List.of(productLine.getQuantity(), productLine.getPrice(), productLine.getProduct().getProduct_id(), cart_id));
        DBUtil.closeConnection(connection);
        System.out.println("Insercion con exito");
    }

    public ArrayList<ProductLine> listProductLinesOfCart(String language, int cart_id) {

        ArrayList<ProductLine> actualLines = new ArrayList<ProductLine>();
        String sql = "SELECT product_Line.* , product.* FROM product_Line , product WHERE cart_id = ? AND product_Line.product_id = product.product_id";
        Connection connection = DBUtil.getConnection();
        try {  
            ResultSet resultSet = DBUtil.select(connection, sql, List.of(cart_id));
            while (resultSet.next()) {
                actualLines.add(new ProductLine(
                        resultSet.getInt("quantity"),
                        new Product(resultSet.getInt("product_id"),
                                resultSet.getInt("stock"),
                                resultSet.getBigDecimal("price"),
                                resultSet.getString("name_" + language),
                                resultSet.getString("description_" + language),
                                resultSet.getInt("category_id"),
                                resultSet.getInt("supplier_id")),
                        resultSet.getBigDecimal("unitPrice")));
            }
            DBUtil.closeConnection(connection);
            return actualLines;
        } catch (SQLException e) {
            throw new RuntimeException("No se puede conectar con la bases de datos");
        }
    }

    public void updateStocks(ArrayList<ProductLine> productLines) {

        Connection connection = DBUtil.getConnection();
            String sql = "UPDATE product SET stock = ? WHERE product_id = ?";
            for (ProductLine productLine : productLines) {
                int stock = productLine.getProduct().getStock() - productLine.getQuantity();
                DBUtil.update(connection, sql, List.of(stock, productLine.getProduct().getProduct_id()));
            }
            DBUtil.closeConnection(connection);
    }

    public void deleteProductLine(int product_id, int cart_id) {

        Connection connection = DBUtil.getConnection();
            String sql = "DELETE FROM product_line WHERE product_id = ? AND cart_id = ?;";
            DBUtil.delete(connection, sql, List.of(product_id, cart_id));
    }

    public void deleteAllProductLines(int cart_id) {

        Connection connection = DBUtil.getConnection();
            String sql = "DELETE FROM product_line WHERE cart_id = ?;";
            DBUtil.delete(connection, sql, List.of(cart_id));
    }
}
