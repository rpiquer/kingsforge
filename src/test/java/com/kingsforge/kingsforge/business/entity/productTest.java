package com.kingsforge.kingsforge.business.entity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class productTest {
    @Nested
    @DisplayName("Test de la clase Product")
    class ProductTest{

        private Product productTest;
        BigDecimal value;

        @BeforeEach
        void setup(){
        this.value = new BigDecimal("0.012");
        this.productTest = new Product(121, 2, value , "nombre", "Descripcion", 12, 1);
        }

        @Test
        void constructorProducts(){
            this.value = new BigDecimal("2");
            Product product = new Product(111, 1, value, "nombre", "Texto", 11, 2);
            assertAll(
                () -> assertEquals(111, product.getProduct_id()),
                () -> assertEquals(1, product.getStock()),
                () -> assertEquals(value, product.getPrice()),
                () -> assertEquals("nombre", product.getName()),
                () -> assertEquals("Texto", product.getDescription()),
                () -> assertEquals(11, product.getCategory_id()),
                () -> assertEquals(2, product.getSupplier_id())
            );
        }

        @DisplayName("Prova de get product id")
        @Test
        void getProduct_idTest(){
            assertEquals(121, productTest.getProduct_id());
        }

        @DisplayName("Prova de get stock")
        @Test
        void getStockTest(){
            assertEquals(2, productTest.getStock());
        }

        @DisplayName("Prova de get rounded price")
        @Test
        void getRoundedPriceTest(){
            this.value = new BigDecimal("0.01");
            assertEquals(value, productTest.getRoundedPrice());
        }

        @DisplayName("Prova de get price")
        @Test
        void getPriceTest(){
            this.value = new BigDecimal("0.012");
            assertEquals(value, productTest.getPrice());
        }

        @DisplayName("Prova de get nom")
        @Test
        void getNameEsTest(){
            assertEquals("nombre", productTest.getName());
        }

        @DisplayName("Prova de get descripció")
        @Test
        void getDescriptionEsTest(){
            assertEquals("Descripcion", productTest.getDescription());
        }

        @DisplayName("Prova de get category id")
        @Test
        void getCategory_idTest(){
            assertEquals(12, productTest.getCategory_id());
        }

        @DisplayName("Prova de get supplier id")
        @Test
        void getSupplier_idTest(){
            assertEquals(1, productTest.getSupplier_id());
        }
       
    }
}
