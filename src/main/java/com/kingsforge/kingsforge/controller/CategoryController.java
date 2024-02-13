package com.kingsforge.kingsforge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kingsforge.kingsforge.business.services.CategoryService;
import com.kingsforge.kingsforge.business.services.ProductService;
import com.kingsforge.kingsforge.business.services.impl.CategoryServiceImpl;
import com.kingsforge.kingsforge.business.services.impl.ProductServiceImpl;

@RequestMapping("{language}/category")
@Controller
public class CategoryController {
    private CategoryService categoryService = new CategoryServiceImpl();
    private ProductService productService = new ProductServiceImpl();

    @GetMapping("/{id}")
    public String getCategory(@PathVariable("id") int id, Model model,@PathVariable("language") String language){
        model.addAttribute("categories", categoryService.listAllCategories(language));
        model.addAttribute("products", productService.getProductsByCategory(id, language));
        model.addAttribute("category", categoryService.getCategoryById(id, language));
        model.addAttribute("language", language);
        return "html/category";
    }
}
