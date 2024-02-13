package com.kingsforge.kingsforge.persistance;

import com.kingsforge.kingsforge.business.entity.Cart;

public interface CartRepository {
    
    public Cart getActiveCart(int user_id);
    public void createActiveCart(int user_id);
    public void closeActiveCart(int cart_id);
}
