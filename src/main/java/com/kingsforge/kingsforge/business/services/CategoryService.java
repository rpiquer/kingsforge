package com.kingsforge.kingsforge.business.services;

import java.util.List;

import com.kingsforge.kingsforge.business.entity.Category;

public interface CategoryService {
    public List<Category> listAllCategories(String language);
    public Category getCategoryById(int id, String language);
}
