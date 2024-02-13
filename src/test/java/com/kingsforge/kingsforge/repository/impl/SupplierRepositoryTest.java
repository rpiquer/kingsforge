package com.kingsforge.kingsforge.repository.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.kingsforge.kingsforge.business.entity.Supplier;
import com.kingsforge.kingsforge.persistance.impl.SupplierRepositoryImpl;

public class SupplierRepositoryTest {
    
    private static SupplierRepositoryImpl supplierRepositoryImpl;

    @BeforeAll
    static void setup(){
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/script_inserciones_tablas.sql")
                .addScript("db/sql/script_inserciones2.sql")
                .build();
        supplierRepositoryImpl = new SupplierRepositoryImpl();
        supplierRepositoryImpl.setDatasource(dataSource);
    }

    @Test
    public void listSuppliers(){
        List<Supplier> supplierList = supplierRepositoryImpl.listSuppliers();
        List<Supplier> expectedSuppliersList = new ArrayList<>();

        Supplier supplier1 = new Supplier(1, "name1");
        expectedSuppliersList.add(supplier1);
        Supplier supplier2 = new Supplier(2, "name2");
        expectedSuppliersList.add(supplier2);

        assertEquals(supplierList, expectedSuppliersList);
    }
    
    @Test
    public void getSupplierById(){
        Supplier supplier = supplierRepositoryImpl.getSupplierById(1, "es");

        assertAll(
            ()-> assertEquals(1, supplier.getSupplier_id()),
            ()-> assertEquals("name1", supplier.getName())
        );
    }

    @Test
    public void getSupplierByNonExistId(){
        assertThrows(
            RuntimeException.class,
            ()-> supplierRepositoryImpl.getSupplierById(666, "es")
        );
    }
}
