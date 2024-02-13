package com.kingsforge.kingsforge.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.kingsforge.kingsforge.business.entity.Category;
import com.kingsforge.kingsforge.persistance.impl.CategoryRepositoryImpl;

public class CategoryRepositoryTest {
    private static CategoryRepositoryImpl categoryRepository;

    @BeforeAll
    static void setup(){
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/script_inserciones_tablas.sql")
                .addScript("db/sql/script_inserciones2.sql")
                .build();
        categoryRepository = new CategoryRepositoryImpl();
        categoryRepository.setDatasource(dataSource);
    }

    @Test
    public void listCategories(){
        Category category = categoryRepository.getCategoryById(0, "es");
        Category expectedCategory = new Category(0, 0, "prueba");
        
        assertEquals(category, expectedCategory);
    
    }

    @Test
    public void listCategories_shouldThrowException(){
        assertThrows(
            RuntimeException.class,
            ()->categoryRepository.getCategoryById(666,"es")
        );
    }

    @Test
    public void listSubcategories(){
        List<Category> categoriesList = categoryRepository.listAllCategories("es");
        List<Category> expectedCategoriesList = new ArrayList<>();

        Category category1 = new Category(1, 0, "name_es1");
        expectedCategoriesList.add(category1);
        Category category2 = new Category(2, 0, "name_es2");
        expectedCategoriesList.add(category2);
        Category subCategory11 = new Category(11, 1, "name_es1");
        expectedCategoriesList.add(subCategory11);
        Category subCategory12 = new Category(12, 1, "name_es2");
        expectedCategoriesList.add(subCategory12);
       
        assertEquals(categoriesList, expectedCategoriesList);
    }
}
