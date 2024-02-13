package com.kingsforge.kingsforge.persistance;

import java.util.List;

import com.kingsforge.kingsforge.business.entity.Product;

public interface ProductRepository {

    public Product getProduct(int product_id, String language) throws RuntimeException;
    public List<Product> getProductsByCategory(int category_id, String language) throws RuntimeException;
    public List<Product> search(String name, String language) throws RuntimeException;
    public List<Product> orderByStock(String language) throws RuntimeException;
    public List<Product> searchByName(String name, String language, String order, int page) throws RuntimeException;
    public List<Product> searchByPrice(String name, String language, String order, int page) throws RuntimeException;
    
}
