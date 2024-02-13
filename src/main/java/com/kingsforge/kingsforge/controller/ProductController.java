package com.kingsforge.kingsforge.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kingsforge.kingsforge.business.entity.Product;
import com.kingsforge.kingsforge.business.services.CategoryService;
import com.kingsforge.kingsforge.business.services.ProductService;
import com.kingsforge.kingsforge.business.services.SupplierService;
import com.kingsforge.kingsforge.business.services.impl.CategoryServiceImpl;
import com.kingsforge.kingsforge.business.services.impl.ProductServiceImpl;
import com.kingsforge.kingsforge.business.services.impl.SupplierServiceImpl;


@RequestMapping("/{language}")
@Controller
public class ProductController {
    private ProductService productService = new ProductServiceImpl();
    private SupplierService supplierService = new SupplierServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();


    @GetMapping("/product/{id}")
    public String getProductDescription(@PathVariable("id") int id, Model model,@PathVariable("language") String language){
        Product product = productService.getProduct(id,language);
        model.addAttribute("prod", product);
        model.addAttribute("language", language);
        model.addAttribute("supplier", supplierService.getSupplierById(product.getSupplier_id(), language));
        model.addAttribute("categories", categoryService.listAllCategories(language));
        return "html/product";

    }

    @GetMapping("/product/result")
    public String search(@RequestParam(name = "page", required = false, defaultValue = "1") String page, @RequestParam(name = "name") String name, @RequestParam(name = "order", required = false, defaultValue = "name-asc") String order, @PathVariable("language") String language, Model model){       
        int pageNumber = Integer.parseInt(page);
        int maxPage = (int)Math.ceil(productService.search(name, language).size() / 10.0);
        List<Product> prodList = new ArrayList<>();
        if (order.equals("name-asc") ) {
            prodList = productService.searchByName(name, language, "asc", pageNumber);
        } else if (order.equals("name-desc")) {
            prodList = productService.searchByName(name, language, "desc", pageNumber);
        } else if (order.equals("price-desc")) {
            prodList = productService.searchByPrice(name, language, "desc", pageNumber);
        }else if (order.equals("price-asc")) {
            prodList = productService.searchByPrice(name, language, "asc", pageNumber);
        }
        model.addAttribute("prodList", prodList);
        model.addAttribute("categories", categoryService.listAllCategories(language));
        model.addAttribute("language", language);
        model.addAttribute("name", name);
        model.addAttribute("maxpage", maxPage);
        model.addAttribute("page", pageNumber);
        model.addAttribute("order", order);
        return "html/result";
    }
/*
    @PostMapping("/product/result")
    public String search(Model model, @PathVariable("language") String language, @RequestParam(name = "name", required = false, defaultValue = "1") String name){
        List<Product> prodList = productService.search(name, language);
        model.addAttribute("prodList", prodList);
        model.addAttribute("categories", categoryService.listAllCategories(language));
        model.addAttribute("language", language);
        return "html/result";
    }
*/
}
