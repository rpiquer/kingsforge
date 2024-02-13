package com.kingsforge.kingsforge.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kingsforge.kingsforge.business.entity.Cart;
import com.kingsforge.kingsforge.business.services.CartService;
import com.kingsforge.kingsforge.business.services.CategoryService;
import com.kingsforge.kingsforge.business.services.impl.CartServiceImpl;
import com.kingsforge.kingsforge.business.services.impl.CategoryServiceImpl;
import com.kingsforge.kingsforge.exceptions.UserException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
    private CartService service = new CartServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    @PostMapping("/{language}/product/{id}")
    public String addNewProductOrder(@PathVariable("id") int id, @PathVariable("language") String language,
            @RequestParam int quantity, HttpServletRequest request, Model model) throws SQLException {
        HttpSession session = request.getSession();
        try {
            service.insertProductLine(id, quantity, session, language);
            return "redirect:/" + language + "/";

        } catch (UserException e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/{language}/cart")
    public String getProductList(Model model, @PathVariable("language") String language, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            Cart cart = service.getActiveCart(language, session);
            model.addAttribute("product_lines", cart.getProductLines());
            model.addAttribute("totalPrice", cart.getTotalPrice());
            model.addAttribute("language", language);
            model.addAttribute("categories", categoryService.listAllCategories(language));
            return "html/cart";

        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{language}/cart")
    public String payCart(@PathVariable("language") String language, HttpServletRequest request, Model model)
            throws SQLException {
        HttpSession session = request.getSession();
        try {
            service.pay(session);
            session.setAttribute("activeCart", service.getActiveCart(language, session));
            return "redirect:/";
        } catch (UserException e) {

            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("{language}/delete/{product_id}")
    public String deleteProductLine(@PathVariable("product_id") int product_id, HttpServletRequest request, @PathVariable("language") String language, Model model) {
        HttpSession session = request.getSession();
        try {
            service.deleteProductLine(product_id, session);
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return  "redirect:/" + language + "/cart";
    }

    @PostMapping("{language}/delete/all")
    public String deleteAllProductLine(HttpServletRequest request, @PathVariable("language") String language, Model model) {
        HttpSession session = request.getSession();
            service.deleteAllProductLines(session);
        return  "redirect:/" + language + "/cart";
    }

}
