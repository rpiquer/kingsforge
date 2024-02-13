package com.kingsforge.kingsforge.business.services.impl;

import java.util.List;

import com.kingsforge.kingsforge.business.entity.Product;
import com.kingsforge.kingsforge.business.services.ProductService;
import com.kingsforge.kingsforge.persistance.ProductRepository;
import com.kingsforge.kingsforge.persistance.impl.ProductRepositoryImpl;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository = new ProductRepositoryImpl();

    public Product getProduct(int product_id,String language){
        return productRepository.getProduct(product_id,language);
    }

    public List<Product> getProductsByCategory(int category_id,String language){
        return productRepository.getProductsByCategory(category_id,language);
    }

    @Override
    public List<Product> search(String name, String language) {
        return productRepository.search(name, language);    
    }

    @Override
    public List<Product> orderByStock(String language) {
        return productRepository.orderByStock(language);
    }

    public List<Product> searchByName(String name, String language, String order, int page){
        return productRepository.searchByName(name, language, order, page);
    }

    public List<Product> searchByPrice(String name, String language, String order, int page){
        return productRepository.searchByPrice(name, language, order, page);
    }
}
