package com.kingsforge.kingsforge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kingsforge.kingsforge.business.services.CategoryService;
import com.kingsforge.kingsforge.business.services.SupplierService;
import com.kingsforge.kingsforge.business.services.impl.CategoryServiceImpl;
import com.kingsforge.kingsforge.business.services.impl.SupplierServiceImpl;

@RequestMapping("/{language}")
@Controller
public class SupplierController {
    private SupplierService supplierService = new SupplierServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    @GetMapping("/suppliers")
    public String getSupplierList(@PathVariable("language") String language, Model model){     
        model.addAttribute("suppliers", supplierService.listSuppliers());
        model.addAttribute("categories", categoryService.listAllCategories(language));
        model.addAttribute("language", language);
        return "html/suppliers";
    }

    @GetMapping("/supplier/{id}")
    public String getProductDescription(@PathVariable("id") int id, Model model, @PathVariable("language") String language){
        model.addAttribute("supp", supplierService.getSupplierById(id,language));
        model.addAttribute("categories", categoryService.listAllCategories(language));
        model.addAttribute("language", language);
        return "html/supplier";
    }

}
