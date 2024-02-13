package com.kingsforge.kingsforge.persistance;

import com.kingsforge.kingsforge.business.entity.Cart;
import com.kingsforge.kingsforge.business.entity.ProductLine;

import jakarta.servlet.http.HttpSession;

public interface CheckingRepository {

    public boolean verifyPassword(String password, String username);
    public String encryptPassword(String password);
    public boolean verifyLogged(HttpSession session);
    public void verifyUpdateStocks(Cart cart);
    public boolean verifyDeleteProductLine(int cart_id, int product_id);
    public boolean verifyInsertProductLine(ProductLine productLine, int cart_id);
}
