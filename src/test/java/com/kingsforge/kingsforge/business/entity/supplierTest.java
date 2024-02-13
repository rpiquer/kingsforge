package com.kingsforge.kingsforge.business.entity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class supplierTest {
    @Nested
    @DisplayName("Test de la clase Supplier")

    class SupplierTest{

        private Supplier supplierTest;

        @BeforeEach
        void setup(){
        this.supplierTest = new Supplier(2 ,2 ,"Descripcion" ,"Nombre" ,"Url" ,2 ,"Calle" ,2 ,"Ciudad" ,"Pais");
    }

        @Test
        void completeConstructorSupplier(){
            Supplier supplier = new Supplier(1, 123456789, "Texto", "nombre", "homepage", 1, "calle", 1, "ciudad", "pais");
            assertAll(
                () -> assertEquals(1, supplier.getSupplier_id()),
                () -> assertEquals(123456789, supplier.getContactPhone()),
                () -> assertEquals("Texto", supplier.getDescription()),
                () -> assertEquals("nombre", supplier.getName()),
                () -> assertEquals("homepage", supplier.getHomepage()),
                () -> assertEquals(1, supplier.getPostalCode()),
                () -> assertEquals("calle", supplier.getStreet()),
                () -> assertEquals(1, supplier.getNumber()),
                () -> assertEquals("ciudad", supplier.getCity()),
                () -> assertEquals("pais", supplier.getCountry())
            );
        }

        @Test
        void nameAndIdConstructorSupplier(){
            Supplier supplier = new Supplier(1,  "nombre");
            assertAll(
                () -> assertEquals(1, supplier.getSupplier_id()),
                () -> assertEquals("nombre", supplier.getName())
            );
        }

        @DisplayName("Prova de get supplier id")
        @Test
        void getSupplier_idTest(){
            assertEquals(2, supplierTest.getSupplier_id());
        }

        @DisplayName("Prova de get número de contacte")
        @Test
        void getStockTest(){
            assertEquals(2, supplierTest.getContactPhone());
        }

        @DisplayName("Prova de get la descripció")
        @Test
        void getDescriptionEsTest(){
            assertEquals("Descripcion", supplierTest.getDescription());
        }

        @DisplayName("Prova de get nom")
        @Test
        void getNameEsTest(){
            assertEquals("Nombre", supplierTest.getName());
        }

        @DisplayName("Prova de get la pàgina del distribuidor")
        @Test
        void getHomepageTest(){
            assertEquals("Url", supplierTest.getHomepage());
        }

        @DisplayName("Prova de get el codi postal")
        @Test
        void getPostalCodeTest(){
            assertEquals(2, supplierTest.getPostalCode());
        }

        @DisplayName("Prova de get el carrer del distribuidor")
        @Test
        void getStreetTest(){
            assertEquals("Calle", supplierTest.getStreet());
        }

        @DisplayName("Prova de get el número del carrer")
        @Test
        void getNumberTest(){
            assertEquals(2, supplierTest.getNumber());
        }

        @DisplayName("Prova de get ciutat del distribuidor")
        @Test
        void getCityTest(){
            assertEquals("Ciudad", supplierTest.getCity());
        }

        @DisplayName("Prova de get el país del distribuidor")
        @Test
        void getCountryTest(){
            assertEquals("Pais", supplierTest.getCountry());
        }

    }
}
