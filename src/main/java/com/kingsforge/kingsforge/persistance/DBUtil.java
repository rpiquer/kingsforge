package com.kingsforge.kingsforge.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DBUtil {
 
    /*
     static final String DBDRIVER = "jdbc:mysql";
    static final String DBHOST = "kingsforge.csyemqqknteo.us-east-1.rds.amazonaws.com:3306";
    static final String DBNAME = "kingsforge";
    static final String DBUSER = "root";
    static final String DBPASSWD = "rootroot";
    
    static String connectionString = String.format("%s://%s/%s?user=%s&password=%s", DBDRIVER, DBHOST, DBNAME, DBUSER, DBPASSWD);
     */

    final static String DRIVER = "jdbc:mysql";
    final static String URL = "//192.168.59.104:3006";
    final static String DB = "kingsforge";
    final static String USER = "root";
    final static String PASSWORD = "root";
     
    static String connectionString = String.format("%s://%s/%s?user=%s&password=%s", DRIVER, URL, DB, USER, PASSWORD);
 
    
    private static DataSource datasource;
    public static DataSource getDataSource(){
        if(datasource == null){
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://192.168.59.104:3006");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            datasource = dataSource;
        }
        return datasource;
    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(connectionString);            
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la bbdd");
        }
    }

    public static void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexción con la bbdd");
        }
    }

    public static ResultSet select(Connection connection, String sql, List<Object> values) {
        try {
            PreparedStatement preparedStatement = setParameters(connection, sql, values);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException();    
        }
    }

    public static boolean insert(Connection connection, String sql, List<Object> values){
        try {
            PreparedStatement preparedStatement = setParameters(connection, sql, values);
            return (preparedStatement.executeUpdate()>0)?true:false;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static int update(Connection connection, String sql, List<Object> values){
        try {
            PreparedStatement preparedStatement = setParameters(connection, sql, values);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static int delete(Connection connection, String sql, List<Object> values){
        try {
            PreparedStatement preparedStatement = setParameters(connection, sql, values);
            return preparedStatement.executeUpdate();  
        } catch (SQLException e) {
            throw new RuntimeException();    
        }
    }

    private static PreparedStatement setParameters(Connection connection, String sql, List<Object> values){
        try {
            PreparedStatement preparedStatement =  connection.prepareStatement(sql);
            if(values != null) {
                for(int i=0;i<values.size();i++) {
                    Object value=values.get(i);
                    preparedStatement.setObject(i+1,value);
                }
            }    
            return preparedStatement;                        
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }   
    
}
