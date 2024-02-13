package com.kingsforge.kingsforge.business.entity;

public class Category {

    private int category_id, father_id;
    private String name;

    public Category(int category_id, int father_id, String name) {
        this.category_id = category_id;
        this.father_id = father_id;
        this.name = name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getFather_id() {
        return father_id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        if (category_id != other.category_id)
            return false;
        if (father_id != other.father_id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Category [category_id=" + category_id + ", father_id=" + father_id + ", name=" + name + "]";
    }

}
