package com.kingsforge.kingsforge.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kingsforge.kingsforge.business.entity.Category;
import com.kingsforge.kingsforge.persistance.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl service;
    
    private Category category1;
    private Category category2;
    private Category category3;

    @BeforeEach
    void setup(){
        this.category1 = new Category(1,0,"Armas");
        this.category2 = new Category(2,1,"Espadas y Dagas");
        this.category3 = new Category(3,1,"Hachas");
    }


    @DisplayName("Test listAllCategories(language)")
    @Test
    public void given0FatherId_ShouldReturnMainCategory() throws RuntimeException {

        ArrayList<Category> catList = new ArrayList<>(List.of(category1,category2,category3));

        when(repository.listAllCategories("es")).thenReturn(catList);

        List<Category> actual = service.listAllCategories("es");

        assertEquals(catList, actual);
        assertEquals(catList, actual);
    }

    @DisplayName("Test getCategoryById(int, language) not exist")
    @Test
    public void givenNotExistingCatById_ShouldThrow() throws RuntimeException {

        when(repository.getCategoryById(-1,"es")).thenThrow(
            RuntimeException.class
        );

        assertThrows(
            RuntimeException.class,
            () -> service.getCategoryById(-1,"es"),
            "Expected RuntimeException exception, but it was not thrown."
        );
    }

    @DisplayName("Test getCategoryById(int, language) exist")
    @Test
    public void givenExistingCatId_ShouldReturnCat() throws RuntimeException {

        when(repository.getCategoryById(1,"es")).thenReturn(category1);

        Category actual = service.getCategoryById(1,"es");

        assertEquals(category1, actual);
    }
    
}

        
