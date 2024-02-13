package com.kingsforge.kingsforge.persistance.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.kingsforge.kingsforge.business.entity.Supplier;
import com.kingsforge.kingsforge.persistance.DBUtil;
import com.kingsforge.kingsforge.persistance.SupplierRepository;

public class SupplierRepositoryImpl implements SupplierRepository {

    private JdbcTemplate jdbcTemplate;

    public SupplierRepositoryImpl() {
        this.jdbcTemplate = new JdbcTemplate(DBUtil.getDataSource());
    }

    public void setDatasource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public List<Supplier> listSuppliers() throws RuntimeException{
        Connection connection = DBUtil.getConnection();

        try {
            List<Supplier> allSuppliers = new ArrayList<Supplier>();
            String sql = "SELECT * FROM supplier";
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                allSuppliers.add(new Supplier(resultSet.getInt("supplier_id"), resultSet.getString("name")));
            }

            DBUtil.closeConnection(connection);
            return allSuppliers;

        } catch (SQLException e) {
            throw new RuntimeException("No se puede conectar con la bases de datos");
        }
    }

    public Supplier getSupplierById(int supplier_id, String language) throws RuntimeException{
        Connection connection = DBUtil.getConnection();

        try {
            Supplier supplier = null;
            String sql = "SELECT * FROM supplier where supplier_id = ?";
            ResultSet resultSet = DBUtil.select(connection, sql, List.of(supplier_id));
            if (resultSet.next()) {
                supplier = new Supplier(resultSet.getInt("supplier_id"), resultSet.getInt("contactPhone"), resultSet.getString("description_" +language), resultSet.getString("name"), resultSet.getString("homepage"), resultSet.getInt("postalCode"),
                resultSet.getString("street"), resultSet.getInt("number"), resultSet.getString("city"), resultSet.getString("country"));
            }

            DBUtil.closeConnection(connection);

            if(supplier == null){
                throw new RuntimeException("No se ha encontrado ningun proveedor");
            }
            return supplier;
        } catch (SQLException e) {
            throw new RuntimeException("No se ha encontrado el proveedor");
        }
    }
}
