package com.kingsforge.kingsforge.persistance.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.kingsforge.kingsforge.exceptions.UserException;
import com.kingsforge.kingsforge.persistance.CustomerRepository;
import com.kingsforge.kingsforge.persistance.DBUtil;

public class CustomerRepositoryImpl implements CustomerRepository {

    public int getUser_id(String username) throws UserException {

        try {
            Connection connection = DBUtil.getConnection();
            String sql = "SELECT customer_id from customer WHERE username = ?";
            ResultSet resultSet = DBUtil.select(connection, sql, List.of(username));
            if (resultSet.next()) {
                return resultSet.getInt("customer_id");
            } else
                throw new UserException("Tu id de usuario no es alcanzable");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public boolean existUsername(String username) {
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "SELECT username from customer WHERE username = ?";
            return DBUtil.select(connection, sql, List.of(username)).next();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public boolean existEmail(String email) {
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "SELECT email from customer WHERE email = ?";
            return DBUtil.select(connection, sql, List.of(email)).next();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void addUser(String username, String password, String email) {
        Connection connection = DBUtil.getConnection();
        String sql = "INSERT INTO customer (username, password, email) VALUES (?, ?, ?)";
        DBUtil.insert(connection, sql, List.of(username, password, email));
    }

}
