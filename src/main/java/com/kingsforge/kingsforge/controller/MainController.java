package com.kingsforge.kingsforge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kingsforge.kingsforge.business.services.CategoryService;
import com.kingsforge.kingsforge.business.services.ProductService;
import com.kingsforge.kingsforge.business.services.impl.CategoryServiceImpl;
import com.kingsforge.kingsforge.business.services.impl.ProductServiceImpl;



@Controller
public class MainController {
    private CategoryService categoryService = new CategoryServiceImpl();
    private ProductService productService = new ProductServiceImpl();
       
    @GetMapping("/")
    public String landing(){
        return "redirect:/es/";
    }

    @GetMapping("/{language}/")
    public String index(@PathVariable("language") String language, Model model){
        model.addAttribute("categories", categoryService.listAllCategories(language));
        model.addAttribute("products", productService.orderByStock(language));
        model.addAttribute("language", language);
        return "index";
    }

    @GetMapping("/{language}/about-us")
    public String about(@PathVariable("language") String language, Model model){
        model.addAttribute("categories", categoryService.listAllCategories(language));
        model.addAttribute("language", language);
        return "html/about";
    }

}