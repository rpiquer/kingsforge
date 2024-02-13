package com.kingsforge.kingsforge.business.services.impl;

import java.sql.SQLException;

import com.kingsforge.kingsforge.business.entity.Cart;
import com.kingsforge.kingsforge.business.entity.Product;
import com.kingsforge.kingsforge.business.entity.ProductLine;
import com.kingsforge.kingsforge.business.services.CartService;
import com.kingsforge.kingsforge.exceptions.UserException;
import com.kingsforge.kingsforge.persistance.CartRepository;
import com.kingsforge.kingsforge.persistance.ProductLineRepository;
import com.kingsforge.kingsforge.persistance.ProductRepository;
import com.kingsforge.kingsforge.persistance.CheckingRepository;
import com.kingsforge.kingsforge.persistance.impl.CartRepositoryImpl;
import com.kingsforge.kingsforge.persistance.impl.ProductLineRepositoryImpl;
import com.kingsforge.kingsforge.persistance.impl.ProductRepositoryImpl;
import com.kingsforge.kingsforge.persistance.impl.CheckingRepositoryImpl;

import jakarta.servlet.http.HttpSession;

public class CartServiceImpl implements CartService {
    private CartRepository cartRepository = new CartRepositoryImpl();
    private ProductLineRepository productLineRepository = new ProductLineRepositoryImpl();
    private CheckingRepository securityRepository = new CheckingRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();

    public Cart getActiveCart(String language, HttpSession session) {

        if (!securityRepository.verifyLogged(session)) {
            throw new UserException("No estas loggeado");
        }
        Cart cart = cartRepository.getActiveCart((int) session.getAttribute("user_id"));
        cart.setProductLines(productLineRepository.listProductLinesOfCart(language, cart.getCart_id()));
        return cart;
    }

    public void pay(HttpSession session) throws SQLException {

        if (!securityRepository.verifyLogged(session)) {
            throw new UserException("No estas loggeado");
        }
        Cart cart = (Cart) session.getAttribute("activeCart");
        if (cart.getProductLines().isEmpty()) {
            throw new UserException("Añade algo al carrito");
        }
        securityRepository.verifyUpdateStocks(cart);
        cart.setDate();
        productLineRepository.updateStocks(cart.getProductLines());
        cartRepository.closeActiveCart(cart.getCart_id());
        cartRepository.createActiveCart((int) session.getAttribute("user_id"));
    }

    public void insertProductLine(int product_id, int quantity, HttpSession session, String language)
            throws SQLException {

        if (!securityRepository.verifyLogged(session)) {
            throw new UserException("No estas loggeado");
        }
        Product product = productRepository.getProduct(product_id, language);
        ProductLine productLine = new ProductLine(quantity, product, product.getPrice());
        Cart cart = (Cart) session.getAttribute("activeCart");
        int cart_id = cart.getCart_id();
        if (securityRepository.verifyInsertProductLine(productLine,cart_id)) {
            throw new UserException("Ya tienes ese producto en el carrito");
        }
        productLineRepository.insertProductLine(productLine, cart_id);
        cart.addProductLine(productLine);
        session.setAttribute("activeCart", cart);
    }

    public void deleteProductLine(int product_id, HttpSession session) {

        if (!securityRepository.verifyLogged(session)) {
            throw new UserException("No estas loggeado");
        }
        Cart cart = (Cart) session.getAttribute("activeCart");
        if (!securityRepository.verifyDeleteProductLine(cart.getCart_id(), product_id)) {
            throw new UserException("No tienes ese producto en el carrito");
        } 
        cart.deleteProductLine(product_id);
        productLineRepository.deleteProductLine(product_id, cart.getCart_id());
        session.setAttribute("activeCart", cart);
    }

    public void deleteAllProductLines(HttpSession session) {
        if (!securityRepository.verifyLogged(session)) {
            throw new UserException("No estas loggeado");
        }
        Cart cart = (Cart) session.getAttribute("activeCart");
        cart.deleteAllProductLine();
        productLineRepository.deleteAllProductLines(cart.getCart_id());
        session.setAttribute("activeCart", cart);
    }

    public void createActiveCart(HttpSession session) {
        if (!securityRepository.verifyLogged(session)) {
            throw new UserException("No estas loggeado");
        }
            int user_id = (int) session.getAttribute("user_id");
            cartRepository.createActiveCart(user_id);
    }
}
