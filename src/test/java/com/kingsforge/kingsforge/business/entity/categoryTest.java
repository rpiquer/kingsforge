package com.kingsforge.kingsforge.business.entity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class categoryTest {

    @Nested
    @DisplayName("Tests de la clase Category")
    class CategoryTest{

        private Category categoryTest;
        
        @BeforeEach
        void setup(){
        this.categoryTest = new Category(1, 0, "Armas");
        }

        @DisplayName("Prova de constructor")
        @Test
        void constructorCategory(){
            Category category = new Category(11,1,"nombre");
            assertAll(
              () -> assertEquals(11, category.getCategory_id()),
              () -> assertEquals(1, category.getFather_id()),
              () -> assertEquals("nombre", category.getName())
            );
        }

        @DisplayName("Prova de get category id")
        @Test
        void getCategory_idTest(){
            assertEquals(1, categoryTest.getCategory_id());
        }

        @DisplayName("Prova de get father id")
        @Test
        void getFather_idTest(){
            assertEquals(0, categoryTest.getFather_id());
        }

        @DisplayName("Prova de get name")
        @Test
        void getName_esTest(){
            assertEquals("Armas", categoryTest.getName());
        }

    }
}
