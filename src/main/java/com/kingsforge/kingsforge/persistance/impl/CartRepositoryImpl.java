package com.kingsforge.kingsforge.persistance.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.kingsforge.kingsforge.business.entity.Cart;
import com.kingsforge.kingsforge.exceptions.UserException;
import com.kingsforge.kingsforge.persistance.CartRepository;
import com.kingsforge.kingsforge.persistance.DBUtil;

public class CartRepositoryImpl implements CartRepository {

    public void payCart() throws SQLException {

        Connection connection = DBUtil.getConnection();
        Cart cart = new Cart(0);
        cart.setDate();

        String sql = "UPDATE cart SET payed = 1, datePayed = ? WHERE cart_id = (SELECT cart_id FROM (SELECT cart_id FROM cart WHERE payed = 0) AS subquery)";
        String sql2 = "INSERT INTO cart (dateCreated, datePayed, payed) VALUES (?, null, 0)";

        DBUtil.update(connection, sql, List.of(cart.getDate()));
        DBUtil.insert(connection, sql2, List.of(cart.getDate()));
        DBUtil.closeConnection(connection);
    }

    public Cart getActiveCart(int user_id) {

        Connection connection = DBUtil.getConnection();
        String sql = "SELECT cart_id FROM cart WHERE customer_id = ? AND payed = 0";
        try {
            ResultSet resultSet = DBUtil.select(connection, sql, List.of(user_id));
            if (resultSet.next()) {
                return new Cart(resultSet.getInt("cart_id"));
            }
            throw new UserException("No se ha podido encontrar el carrito activo");
        } catch (SQLException e) {
            throw new RuntimeException("No se puede conectar con la bases de datos");
        }
    }

    public void createActiveCart(int user_id) {

        Connection connection = DBUtil.getConnection();
        String sql = "INSERT INTO cart (dateCreated, datePayed, payed, customer_id) VALUES (?, null, 0, ?)";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        DBUtil.insert(connection, sql, List.of(dtf.format(date), user_id));
    }

    public void closeActiveCart(int cart_id) {

        Connection connection = DBUtil.getConnection();
        String sql = "UPDATE cart SET payed = 1, datePayed = ? WHERE cart_id = ?";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        DBUtil.update(connection, sql, List.of(dtf.format(date), cart_id));
    }

}
