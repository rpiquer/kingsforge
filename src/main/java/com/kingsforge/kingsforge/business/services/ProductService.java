package com.kingsforge.kingsforge.business.services;

import java.util.List;

import com.kingsforge.kingsforge.business.entity.Product;

public interface ProductService {
    public Product getProduct(int product_id,String language);
    public List<Product> getProductsByCategory(int category_id,String language); 
    public List<Product> search(String name, String language);
    public List<Product> orderByStock(String language);
    public List<Product> searchByName(String name, String language, String order, int page);
    public List<Product> searchByPrice(String name, String language, String order, int page);
}
