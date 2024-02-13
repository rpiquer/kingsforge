package com.kingsforge.kingsforge.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kingsforge.kingsforge.business.entity.Product;
import com.kingsforge.kingsforge.persistance.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    // Creem el mock object de ProductRepository que utilitzarem en els tests
    @Mock
    private ProductRepository repository;

    // Indiquem que s'utilitzaran els mocks en la classe ProductServiceImpl
    @InjectMocks
    private ProductServiceImpl service;
    
    // Producte que utilitzarem en els tests
    private Product product1;
    private Product product2;

    @BeforeEach
    void setup(){
        this.product1 = new Product(111, 4, new BigDecimal(69.90), "Chafarote Orco", "La espada de batalla orco, con su empuñadura reforzada y su hoja curva ágil. Perfecta para el movimiento rápido en el campo de batalla, con su acabado oscuro, esta espada será la batalla bichos, preferido por toda la horda! Longitud total: 80 cm.", 1, 1);
        this.product2 = new Product(112, 6, new BigDecimal(145.00), "Espada del Merodeador", "Hecha de espuma alrededor de un núcleo de fibra de vidrio y acabado con una pintura fuerte. La versión más larga exhibe un espaciador altamente detallado de 20 cm de largo hecho de espuma de poliuretano, que está pintado a mano y texturizado para parecerse a un metal corroído o erosionado con crecimientos espinosos. El protector cruzado de color acero está adornado con una cara de esqueleto, y el mango está moldeado y pintado para que parezca que ha sido envuelto con la piel desollada de los cobardes. Longitud total: 107 cm.",1, 2);
        }

    @DisplayName("Prova de getProduct(int) exist")
    @Test
    public void givenExistingId_ShouldReturnProduct() throws RuntimeException {

        // Configurem les accions del objecte Mock
        when(repository.getProduct(product1.getProduct_id(),"_es")).thenReturn(product1);

        Product actual = service.getProduct(product1.getProduct_id(),"_es");

        assertSame(product1, actual);
    }

    @DisplayName("Prova de getProduct(int) not exist")
    @Test
    public void givenNonExistingId_ShouldThrowException() throws RuntimeException {

        // Configurem les accions del objecte Mock
        when(repository.getProduct(-1,"_es")).thenThrow(
            RuntimeException.class
        );

        assertThrows(
            RuntimeException.class,
            () -> service.getProduct(-1,"_es"),
            "Expected RuntimeException exception, but it was not thrown."
        );
    }

    @DisplayName("Prova de getProduct(int) different")
    @Test
    public void givenAnotherExistingId_ShouldReturnDifferentProduct() throws RuntimeException {
        
        // Configurem les accions dels objectes Mock
        when(repository.getProduct(product1.getProduct_id(),"_es")).thenReturn(product1);

        Product actual = service.getProduct(product1.getProduct_id(),"_es");

        when(repository.getProduct(product2.getProduct_id(),"_es")).thenReturn(product2);

        Product actual2 = service.getProduct(product2.getProduct_id(),"_es");

        assertNotSame(product2, actual);
        assertSame(product2, actual2);

    }


    @DisplayName("Prova de getProductsByCategory(String category) exist")
    @Test
    public void givenExistingCategoryProducts_ReturnList() throws RuntimeException {

        ArrayList<Product> products = new ArrayList<>(List.of(product1,product2));

        // Configurem les accions del objecte Mock
        when(repository.getProductsByCategory(product1.getCategory_id(),"_es")).thenReturn(products);

        List<Product> actual = service.getProductsByCategory(product1.getCategory_id(),"_es");

        assertEquals(products, actual);
    }

    @DisplayName("Prova de getProductsByCategory(String category) exists only one")
    @Test
    public void givenOneExistingCategoryProducts_ReturnList() throws RuntimeException {

        ArrayList<Product> products = new ArrayList<>(List.of(product1));

        // Configurem les accions del objecte Mock
        when(repository.getProductsByCategory(product1.getCategory_id(),"_es")).thenReturn(products);

        List<Product> actual = service.getProductsByCategory(product1.getCategory_id(),"_es");

        assertEquals(products, actual);
    }

    @DisplayName("Prova de getProductsByCategory(String category) not exists")
    @Test
    public void givenNoExistingCategoryProducts_ShouldThrowException() throws RuntimeException {

        when(repository.getProductsByCategory(24,"_es")).thenThrow(
            RuntimeException.class
        );

        assertThrows(
            RuntimeException.class,
            () -> service.getProductsByCategory(24,"_es"),
            "Expected RuntimeException exception, but it was not thrown."
        );
    }
}