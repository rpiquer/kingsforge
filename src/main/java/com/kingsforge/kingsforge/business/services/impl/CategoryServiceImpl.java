package com.kingsforge.kingsforge.business.services.impl;

import java.util.List;

import com.kingsforge.kingsforge.business.entity.Category;
import com.kingsforge.kingsforge.business.services.CategoryService;
import com.kingsforge.kingsforge.persistance.CategoryRepository;
import com.kingsforge.kingsforge.persistance.impl.CategoryRepositoryImpl;

public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    public List<Category> listAllCategories(String language){
        return categoryRepository.listAllCategories(language);
    }

    public Category getCategoryById(int id, String language){
        return categoryRepository.getCategoryById(id, language);
    }
}
