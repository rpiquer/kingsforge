package com.kingsforge.kingsforge.persistance;

import java.util.List;

import com.kingsforge.kingsforge.business.entity.Category;

public interface CategoryRepository {

    public List<Category> listAllCategories(String language) throws RuntimeException;
    public Category getCategoryById(int id, String language) throws RuntimeException;
    
}
