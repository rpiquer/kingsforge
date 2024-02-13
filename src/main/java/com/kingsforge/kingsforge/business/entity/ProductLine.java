package com.kingsforge.kingsforge.business.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductLine {

    private int quantity;
    private Product product;
    private BigDecimal price;

    public ProductLine(int quantity, Product product, BigDecimal price) {
        this.quantity = quantity;
        this.product = product;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getRoundedPrice() {
        return price.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalPrice(int quantity){
        return (price.multiply(BigDecimal.valueOf(quantity))).setScale(2, RoundingMode.HALF_UP);
    }

    
}
