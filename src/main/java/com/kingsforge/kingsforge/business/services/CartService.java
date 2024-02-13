package com.kingsforge.kingsforge.business.services;

import java.sql.SQLException;

import com.kingsforge.kingsforge.business.entity.Cart;

import jakarta.servlet.http.HttpSession;

public interface CartService {

    public void pay(HttpSession session) throws SQLException;
    public Cart getActiveCart(String language, HttpSession session);
    public void insertProductLine(int product_id, int quantity, HttpSession session, String language) throws SQLException;
    public void deleteProductLine(int product_id, HttpSession session);
    public void deleteAllProductLines(HttpSession session);
    public void createActiveCart(HttpSession session);
    
}
