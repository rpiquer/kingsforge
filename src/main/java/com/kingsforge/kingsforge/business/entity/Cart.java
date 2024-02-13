package com.kingsforge.kingsforge.business.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Cart {

    private LocalDateTime date;
    private int cart_id;
    private ArrayList<ProductLine> productLines;

    public Cart(int cart_id) {
        this.cart_id = cart_id;
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(date);
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setDate() {
        date = LocalDateTime.now();
    }

    public ArrayList<ProductLine> getProductLines() {
        return productLines;
    }

    public ProductLine getProductLineByProductId(int product_id) {
        for (ProductLine productLine : productLines) {
            if (productLine.getProduct().getProduct_id() == product_id) {
                return productLine;
            }
        }
        return null;
    }

    public ProductLine addProductLine(ProductLine productLine) {
        productLines.add(productLine);
        return productLine;
    }

    public void deleteProductLine(int product_id) {
        ArrayList<ProductLine> productList = this.getProductLines();
        productList.remove(this.getProductLineByProductId(product_id));
        this.productLines = productList;
    }

    public void deleteAllProductLine() {
        this.productLines = new ArrayList<ProductLine>();
    }

    public ProductLine setProductLineByProductId(int product_id, ProductLine actual) {
        for (ProductLine productLine : productLines) {
            if (productLine.getProduct().getProduct_id() == product_id) {
                productLines.set(productLines.indexOf(productLine), actual);
                return productLine;
            }
        }
        return null;
    }

    public void setProductLines(ArrayList<ProductLine> productLines) {
        this.productLines = productLines;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        ArrayList<ProductLine> productLines = this.getProductLines();
        for (ProductLine productLine : productLines) {
            totalPrice = totalPrice.add(productLine.getPrice().multiply(BigDecimal.valueOf(productLine.getQuantity())));
        }
        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }
}
