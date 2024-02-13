package com.kingsforge.kingsforge.persistance.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.kingsforge.kingsforge.business.entity.Cart;
import com.kingsforge.kingsforge.business.entity.ProductLine;
import com.kingsforge.kingsforge.exceptions.UserException;
import com.kingsforge.kingsforge.persistance.DBUtil;
import com.kingsforge.kingsforge.persistance.CheckingRepository;

import jakarta.servlet.http.HttpSession;

public class CheckingRepositoryImpl implements CheckingRepository {

    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    public boolean verifyPassword(String password, String username) throws UserException {

        Connection connection = DBUtil.getConnection();
        String sql = "SELECT password from customer WHERE username = ?";
        ResultSet resultSet = DBUtil.select(connection, sql, List.of(username));
        try {
            if (!resultSet.next()) {
                throw new UserException("El usuario no existe");
            }
            else if (!passwordEncryptor.checkPassword(password, resultSet.getString("password"))){
                throw new UserException("La password no coincide");
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("No se puede conectar con la bases de datos");
        }
    }

    public String encryptPassword(String password) {

        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        if (!passwordEncryptor.checkPassword(password, encryptedPassword)) {
            throw new UserException("Ha ocurrido un error con tu contraseña");
        }
        return encryptedPassword;
    }

    public boolean verifyLogged(HttpSession session) {

        return !(session.getAttribute("user_id") == null);
    }

    public void verifyUpdateStocks(Cart cart) {

        for (ProductLine productLine : cart.getProductLines()) {
            if (productLine.getQuantity() > productLine.getProduct().getStock())
                throw new UserException("No hay stock suficiente de: " + productLine.getProduct().getName());
        }
    }

    public boolean verifyDeleteProductLine(int cart_id, int product_id) {

        Connection connection = DBUtil.getConnection();
        String sql = "SELECT * from product_Line WHERE cart_id = ? AND product_id = ?";
        ResultSet resultSet = DBUtil.select(connection, sql, List.of(cart_id, product_id));
        try {
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("No se puede conectar con la bases de datos");
        }
    }

    public boolean verifyInsertProductLine(ProductLine productLine, int cart_id) {

        Connection connection = DBUtil.getConnection();

        try {
            String sql = "SELECT * FROM product_Line WHERE product_id = ? AND cart_id = ?;";
            ResultSet resultSet = DBUtil.select(connection, sql, List.of(productLine.getProduct().getProduct_id(), cart_id));
                    return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("No se puede conectar con la bases de datos");
        }
    }

}
