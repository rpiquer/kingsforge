package com.kingsforge.kingsforge.persistance;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kingsforge.kingsforge.business.entity.ProductLine;

public interface ProductLineRepository {
    
    public void insertProductLine(ProductLine productLine, int cart_id) throws SQLException;
    public void updateStocks(ArrayList<ProductLine> productLines);
    public ArrayList<ProductLine> listProductLinesOfCart(String language, int cart_id);
    public void deleteProductLine(int product_id, int cart_id);
    public void deleteAllProductLines(int cart_id);
}
