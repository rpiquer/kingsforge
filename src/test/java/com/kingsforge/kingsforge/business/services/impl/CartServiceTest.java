package com.kingsforge.kingsforge.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kingsforge.kingsforge.business.entity.Cart;
import com.kingsforge.kingsforge.business.entity.Product;
import com.kingsforge.kingsforge.business.entity.ProductLine;
import com.kingsforge.kingsforge.exceptions.UserException;
import com.kingsforge.kingsforge.persistance.CartRepository;
import com.kingsforge.kingsforge.persistance.CheckingRepository;
import com.kingsforge.kingsforge.persistance.ProductLineRepository;
import com.kingsforge.kingsforge.persistance.ProductRepository;

import jakarta.servlet.http.HttpSession;


@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductLineRepository productLineRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CheckingRepository security;
    @Mock
    private Cart cartMock;

    @InjectMocks
    private CartServiceImpl service;

    private HttpSession session;
    private Product product1;
    private Product product2;
    private ProductLine productLine1;
    private ProductLine productLine2;
    private Cart cart;

    @BeforeEach
    void setup(){
        this.session = mock(HttpSession.class);
        this.product1 = new Product(111, 4, new BigDecimal(69.90), "Chafarote Orco", "La espada de batalla orco, con su empuñadura reforzada y su hoja curva ágil. Perfecta para el movimiento rápido en el campo de batalla, con su acabado oscuro, esta espada será la batalla bichos, preferido por toda la horda! Longitud total: 80 cm.", 1, 1);
        this.product2 = new Product(112, 6, new BigDecimal(145.00), "Espada del Merodeador", "Hecha de espuma alrededor de un núcleo de fibra de vidrio y acabado con una pintura fuerte. La versión más larga exhibe un espaciador altamente detallado de 20 cm de largo hecho de espuma de poliuretano, que está pintado a mano y texturizado para parecerse a un metal corroído o erosionado con crecimientos espinosos. El protector cruzado de color acero está adornado con una cara de esqueleto, y el mango está moldeado y pintado para que parezca que ha sido envuelto con la piel desollada de los cobardes. Longitud total: 107 cm.",1, 2);
        this.productLine1 = new ProductLine(1, product1, product1.getPrice());
        this.productLine2 = new ProductLine(2, product2, product2.getPrice());
        this.cart = new Cart(1);
    }


    @DisplayName("Test getActiveCart(language, session) not logged")
    @Test
    public void getActiveCartGivenNullSession_ShouldThrow() throws RuntimeException {

        when(security.verifyLogged(session)).thenReturn(false);

        assertThrows(
            UserException.class,
            () -> service.getActiveCart("es", session),
            "Expected UserException exception, but it was not thrown."
        );

    }

    @DisplayName("Test getActiveCart(language, session) working")
    @Test
    public void getActiveCartGivenEverythingWorking_ShouldReturnCart() throws RuntimeException {

        ArrayList<ProductLine> productLines = new ArrayList<>(List.of(productLine1,productLine2));
        
        cart.setProductLines(productLines);

        when(security.verifyLogged(session)).thenReturn(true);

        when(cartRepository.getActiveCart(1)).thenReturn(cart);

        when(productLineRepository.listProductLinesOfCart("es", 1)).thenReturn(productLines);

        when(session.getAttribute("user_id")).thenReturn(1);

        Cart actual = service.getActiveCart("es", session);

        assertEquals(cart, actual);

    }

    @DisplayName("Test pay(session) not logged")
    @Test
    public void payGivenNullSession_ShouldThrow() throws RuntimeException {

        when(security.verifyLogged(session)).thenReturn(false);

        assertThrows(
            UserException.class,
            () -> service.pay(session),
            "Expected UserException exception, but it was not thrown."
        );

    }

    @DisplayName("Test pay(session) empty Cart")
    @Test
    public void payEmptyCart_ShouldThrow() throws RuntimeException {

        ArrayList<ProductLine> productLines = new ArrayList<>(List.of());

        when(security.verifyLogged(session)).thenReturn(true);

        cart.setProductLines(productLines);

        when(session.getAttribute("activeCart")).thenReturn(cart);

        assertThrows(
            UserException.class,
            () -> service.pay(session),
            "Expected UserException exception, but it was not thrown."
        );

    }

    @DisplayName("Test pay(session) working")
    @Test
    public void payGivenEverythingWorking_ShouldDoChanges() throws RuntimeException, SQLException {

        ArrayList<ProductLine> productLines = new ArrayList<>(List.of(productLine1,productLine2));

        when(security.verifyLogged(session)).thenReturn(true);

        cart.setProductLines(productLines);

        when(session.getAttribute("activeCart")).thenReturn(cart);

        when(session.getAttribute("user_id")).thenReturn(1);

        service.pay(session);

        assertAll(
                     () -> assertNotNull(cart.getDate()),
                     () -> verify(security).verifyUpdateStocks(cart),
                     () -> verify(productLineRepository).updateStocks(productLines),
                     () -> verify(cartRepository).closeActiveCart(1),
                     () -> verify(cartRepository).createActiveCart(1)
                );

    }

    @DisplayName("Test insertProductLine(product_id, quantity, session, language) not logged")
    @Test
    public void insertProductLineGivenNullSession_ShouldThrow() throws RuntimeException {

        when(security.verifyLogged(session)).thenReturn(false);

        assertThrows(
            UserException.class,
            () -> service.insertProductLine(1,1,session, "es"),
            "Expected UserException exception, but it was not thrown."
        );

    }


    @DisplayName("Test insertProductLine(product_id, quantity, session, language) allready in Cart")
    @Test
    public void insertProductLineGivenAllreadyInCartItem_ShouldThrow() throws RuntimeException {
        
        when(security.verifyLogged(session)).thenReturn(true);

        when(productRepository.getProduct(111,"es")).thenReturn(product1);

        when(session.getAttribute("activeCart")).thenReturn(cartMock);

        when(cartMock.getCart_id()).thenReturn(1);

        when(security.verifyInsertProductLine(any(ProductLine.class),eq(1))).thenReturn(true);

        assertThrows(
            UserException.class,
            () -> service.insertProductLine(111, 1, session, "es"),
            "Expected UserException exception, but it was not thrown."
        );
        }

    @DisplayName("Test insertProductLine(product_id, quantity, session, language) everything working")
    @Test
    public void insertProductLineGivenEverythingWorking() throws RuntimeException, SQLException {
        
        when(security.verifyLogged(session)).thenReturn(true);

        when(productRepository.getProduct(111,"es")).thenReturn(product1);

        when(session.getAttribute("activeCart")).thenReturn(cartMock);

        when(cartMock.getCart_id()).thenReturn(1);

        when(security.verifyInsertProductLine(any(ProductLine.class),eq(1))).thenReturn(false);

        service.insertProductLine(111, 1, session, "es");

        assertAll(
                     () -> verify(productLineRepository).insertProductLine(any(),eq(1)),
                     () -> verify(cartMock).addProductLine(any()),
                     () -> verify(session).setAttribute("activeCart",cartMock)
                );

        }

    @DisplayName("Test deleteProductLine(product_id,session) not logged")
    @Test
    public void deleteProductLineGivenNullSession_ShouldThrow() throws RuntimeException {

        when(security.verifyLogged(session)).thenReturn(false);

        assertThrows(
            UserException.class,
            () -> service.deleteProductLine(111,session),
            "Expected UserException exception, but it was not thrown."
        );

    }

    @DisplayName("Test deleteProductLine(product_id,session) not in cart")
    @Test
    public void deleteProductLineNotInCart_ShouldThrow() throws RuntimeException {

        when(security.verifyLogged(session)).thenReturn(true);

        when(session.getAttribute("activeCart")).thenReturn(cart);

        when(security.verifyDeleteProductLine(1, 111)).thenReturn(false);

        assertThrows(
            UserException.class,
            () -> service.deleteProductLine(111,session),
            "Expected UserException exception, but it was not thrown."
        );

    }

    @DisplayName("Test deleteProductLine(product_id,session) everything working")
    @Test
    public void deleteProductLineGivenEverythingWorking() throws RuntimeException {

        ArrayList<ProductLine> productLines = new ArrayList<>(List.of(productLine1,productLine2));

        cart.setProductLines(productLines);

        when(security.verifyLogged(session)).thenReturn(true);

        when(session.getAttribute("activeCart")).thenReturn(cart);

        when(security.verifyDeleteProductLine(1, product1.getProduct_id())).thenReturn(true);

        service.deleteProductLine(product1.getProduct_id(),session);

        assertAll(
                     () -> assertEquals(cart.getProductLines(),List.of(productLine2)), //comprobamos que efectivamente se borran
                     () -> verify(productLineRepository).deleteProductLine(product1.getProduct_id(),cart.getCart_id()),
                     () -> verify(session).setAttribute("activeCart",cart)
                );

    }

    @DisplayName("Test deleteAllProductLines(product_id,session) not logged")
    @Test
    public void deleteAllProductLinesGivenNullSession_ShouldThrow() throws RuntimeException {

        when(security.verifyLogged(session)).thenReturn(false);

        assertThrows(
            UserException.class,
            () -> service.deleteAllProductLines(session),
            "Expected UserException exception, but it was not thrown."
        );

    }

    @DisplayName("Test deleteAllProductLines(product_id,session) not logged")
    @Test
    public void deleteAllProductLinesGivenEverythingWorking() throws RuntimeException {

        ArrayList<ProductLine> productLines = new ArrayList<>(List.of(productLine1,productLine2));
        cart.setProductLines(productLines);

        when(security.verifyLogged(session)).thenReturn(true);

        when(session.getAttribute("activeCart")).thenReturn(cart);

        service.deleteAllProductLines(session);

        assertAll(
            () -> assertTrue(cart.getProductLines().isEmpty()), //comprobamos que efectivamente se borran
            () -> verify(productLineRepository).deleteAllProductLines(cart.getCart_id()),
            () -> verify(session).setAttribute("activeCart", cart)
       );

    }

    @DisplayName("Test createActiveCart(product_id,session) not logged")
    @Test
    public void createActiveCartGivenNullSession_ShouldThrow() throws RuntimeException {

        when(security.verifyLogged(session)).thenReturn(false);

        assertThrows(
            UserException.class,
            () -> service.createActiveCart(session),
            "Expected UserException exception, but it was not thrown."
        );

    }

    @DisplayName("Test createActiveCart(product_id,session) logged")
    @Test
    public void createActiveCartGivenLogged() throws RuntimeException {

        when(security.verifyLogged(session)).thenReturn(true);

        when(session.getAttribute("user_id")).thenReturn(1);

        service.createActiveCart(session);

        assertAll(
            () -> verify(cartRepository).createActiveCart(1)
       );

    }
    }