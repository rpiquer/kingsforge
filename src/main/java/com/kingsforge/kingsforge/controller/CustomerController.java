package com.kingsforge.kingsforge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kingsforge.kingsforge.business.services.CartService;
import com.kingsforge.kingsforge.business.services.CategoryService;
import com.kingsforge.kingsforge.business.services.CustomerService;
import com.kingsforge.kingsforge.business.services.impl.CartServiceImpl;
import com.kingsforge.kingsforge.business.services.impl.CategoryServiceImpl;
import com.kingsforge.kingsforge.business.services.impl.CustomerServiceImpl;
import com.kingsforge.kingsforge.exceptions.UserException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/{language}/user")
@Controller
public class CustomerController {
    private CustomerService service = new CustomerServiceImpl();
    private CartService cartService = new CartServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    @GetMapping("/login")
    public String loginSetUp(@PathVariable("language") String language, Model model) {
        model.addAttribute("language", language);
        model.addAttribute("categories", categoryService.listAllCategories(language));
        return "html/login";
    }

    @GetMapping("/register")
    public String registerSetUp(@PathVariable("language") String language, Model model) {
        model.addAttribute("language", language);
        model.addAttribute("categories", categoryService.listAllCategories(language));
        return "html/register";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, @PathVariable("language") String language,
            @RequestParam String username, @RequestParam String password, Model model) {
        HttpSession session = request.getSession();
            try {
                int user_id = service.login(username, password, session);
                session.setAttribute("user_id", user_id);
                session.setAttribute("activeCart", cartService.getActiveCart(language, session));
                return "redirect:/";
            } catch (UserException e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request, @PathVariable("language") String language,
            @RequestParam String username, @RequestParam String password, Model model, @RequestParam String email) {
        HttpSession session = request.getSession();
            try {
                int user_id = service.register(username, password, email, session);
                session.setAttribute("user_id", user_id);
                cartService.createActiveCart(session);
                session.setAttribute("activeCart", cartService.getActiveCart(language, session));
                return "redirect:/";
            } catch (UserException e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            } catch (Exception e) {
                model.addAttribute("error", "Error 404");
                return "error";
            }
    }

}
