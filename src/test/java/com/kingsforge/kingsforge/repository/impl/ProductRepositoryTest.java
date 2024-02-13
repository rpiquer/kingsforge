package com.kingsforge.kingsforge.repository.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.kingsforge.kingsforge.business.entity.Product;
import com.kingsforge.kingsforge.persistance.impl.ProductRepositoryImpl;

public class ProductRepositoryTest {
    public static ProductRepositoryImpl productRepositoryImpl;

    @BeforeAll
    static void setup(){
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/script_inserciones_tablas.sql")
                .addScript("db/sql/script_inserciones2.sql")
                .build();
            productRepositoryImpl = new ProductRepositoryImpl();
            productRepositoryImpl.setDatasource(dataSource);
    }

    @Test
    public void getProducts(){
        Product product = productRepositoryImpl.getProduct(1, "es"); 
        Product expectedProduct = new Product(1, 1, new BigDecimal(1), "name_es1", "descripcion_es1", 1, 1);
        
        assertEquals(product, expectedProduct);
    }

    @Test
    public void getProduct_shouldReturnProduct(){
        assertThrows(
            RuntimeException.class,
            ()-> productRepositoryImpl.getProduct(666,"es")
        );
    }

    @Test
    public void getProductsByCategory(){
        List<Product> productsList = productRepositoryImpl.getProductsByCategory(1,"es");
        List<Product> expectedProductsList = new ArrayList<>();

        Product product1 =  new Product(1, 1, new BigDecimal(1), "name_es1", "descripcion_es1", 1, 1);
        expectedProductsList.add(product1);

        assertEquals(productsList, expectedProductsList);
    } 
    //No hemos hecho un test en el que de error porque queremos que nos devuelva la lista vacía lo que no hace si da una excepción

    @Test
    public void searching(){
        List<Product> productsList = productRepositoryImpl.search("name_es1", "es");

        List<Product> expectedProductsList = new ArrayList<>();
        Product product1 =  new Product(1, 1, new BigDecimal(1), "name_es1", "descripcion_es1", 1, 1);
        expectedProductsList.add(product1);

        assertEquals(productsList, expectedProductsList);
    }


    @Test
    public void orderByStock(){
        List<Product> productsList = productRepositoryImpl.orderByStock("es");
        List<Product> expectedProductsList = new ArrayList<>();
        
        Product product2 =  new Product(2, 2, new BigDecimal(2), "name_es2", "descripcion_es2", 2, 2);
        expectedProductsList.add(product2);
        Product product1 =  new Product(1, 1, new BigDecimal(1), "name_es1", "descripcion_es1", 1, 1);
        expectedProductsList.add(product1);

        assertEquals(productsList, expectedProductsList);
    }

    @Test
    public void searchByName(){
        List<Product> productsListasc = productRepositoryImpl.searchByName("name_es", "es", "asc", 1);
        List<Product> expectedProductsListasc = new ArrayList<>();


        Product product1asc =  new Product(1, 1, new BigDecimal(1), "name_es1", "descripcion_es1", 1, 1);
        expectedProductsListasc.add(product1asc);
        Product product2asc =  new Product(2, 2, new BigDecimal(2), "name_es2", "descripcion_es2", 2, 2);
        expectedProductsListasc.add(product2asc);
       
       
        List<Product> productsListdesc = productRepositoryImpl.searchByName("name_es", "es", "desc", 1);
        List<Product> expectedProductsListdesc = new ArrayList<>();


        Product product2desc =  new Product(2, 2, new BigDecimal(2), "name_es2", "descripcion_es2", 2, 2);
        expectedProductsListdesc.add(product2desc);
        Product product1desc =  new Product(1, 1, new BigDecimal(1), "name_es1", "descripcion_es1", 1, 1);
        expectedProductsListdesc.add(product1desc);

        assertAll(
            ()-> assertEquals(productsListasc, expectedProductsListasc),
            ()-> assertEquals(productsListdesc, expectedProductsListdesc)
        );
    }


    @Test
    public void searchByName_shouldReturnProduct(){
        assertThrows(
            RuntimeException.class,
            ()->productRepositoryImpl.searchByName("namsdadsade_es1", "es", "asc", 11)
        );
    }


    @Test
    public void searchByPrice(){
        List<Product> productsListasc = productRepositoryImpl.searchByPrice("name_es", "es", "asc", 1);
        List<Product> expectedProductsListasc = new ArrayList<>();


        Product product1asc =  new Product(1, 1, new BigDecimal(1), "name_es1", "descripcion_es1", 1, 1);
        expectedProductsListasc.add(product1asc);
        Product product2asc =  new Product(2, 2, new BigDecimal(2), "name_es2", "descripcion_es2", 2, 2);
        expectedProductsListasc.add(product2asc);
       
        List<Product> productsListdesc = productRepositoryImpl.searchByPrice("name_es", "es", "desc", 1);
        List<Product> expectedProductsListdesc = new ArrayList<>();

        Product product2desc =  new Product(2, 2, new BigDecimal(2), "name_es2", "descripcion_es2", 2, 2);
        expectedProductsListdesc.add(product2desc);
        Product product1desc =  new Product(1, 1, new BigDecimal(1), "name_es1", "descripcion_es1", 1, 1);
        expectedProductsListdesc.add(product1desc);

        assertAll(
            ()-> assertEquals(productsListasc, expectedProductsListasc),
            ()-> assertEquals(productsListdesc, expectedProductsListdesc)
        );
    }


    @Test
    public void searchByPrice_shouldReturnProduct(){
        assertThrows(
            RuntimeException.class,
            ()->productRepositoryImpl.searchByPrice("namsdadsade_es1", "es", "asc", 11)
        );
    }

}
