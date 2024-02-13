package com.kingsforge.kingsforge.business.entity;

public class Supplier {
    private int supplier_id;
    private int contactPhone;
    private String description;
    private String name;
    private String homepage;
    private int postalCode;
    private String street;
    private int number;
    private String city;
    private String country;

    public Supplier(int supplier_id, String name) {
        this.supplier_id = supplier_id;
        this.name = name;
    }

    public Supplier(int supplier_id, int contactPhone, String description, String name, String homepage, int postalCode,
            String street, int number, String city, String country) {
        this.supplier_id = supplier_id;
        this.contactPhone = contactPhone;
        this.description = description;
        this.name = name;
        this.homepage = homepage;
        this.postalCode = postalCode;
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public int getContactPhone() {
        return contactPhone;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Supplier [supplier_id=" + supplier_id + ", contactPhone=" + contactPhone + ", description="
                + description + ", name=" + name + ", homepage=" + homepage + ", postalCode=" + postalCode + ", street="
                + street + ", number=" + number + ", city=" + city + ", country=" + country + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Supplier other = (Supplier) obj;
        if (supplier_id != other.supplier_id)
            return false;
        if (contactPhone != other.contactPhone)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (homepage == null) {
            if (other.homepage != null)
                return false;
        } else if (!homepage.equals(other.homepage))
            return false;
        if (postalCode != other.postalCode)
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (number != other.number)
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        return true;
    }

}