package com.kingsforge.kingsforge.business.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {

    private int stock, category_id, product_id, supplier_id;
    private BigDecimal price;
    private String name, description;

    public Product(int product_id, int stock, BigDecimal price, String name, String description, int category_id, int supplier_id) {
        this.product_id = product_id;
        this.stock = stock;
        this.price = price;
        this.name = name;
        this.description = description;
        this.category_id = category_id;
        this.supplier_id = supplier_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getStock() {
        return stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public BigDecimal getRoundedPrice() {
        return price.setScale(2, RoundingMode.HALF_UP);
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (stock != other.stock)
            return false;
        if (category_id != other.category_id)
            return false;
        if (product_id != other.product_id)
            return false;
        if (supplier_id != other.supplier_id)
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [stock=" + stock + ", category_id=" + category_id + ", product_id=" + product_id
                + ", supplier_id=" + supplier_id + ", price=" + price + ", name=" + name + ", description="
                + description + "]";
    }

}

