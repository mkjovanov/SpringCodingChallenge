package ava.coding.challenge.main.product.entities;

import ava.coding.challenge.repository.IEntity;

public class Product implements IEntity {

    private String id;
    private String name;
    private Double price;
    private Integer stock;
    private String organization;

    public Product() { }
    public Product(String id, String name, double price, int stock, String organization) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.organization = organization;
    }
    public Product(String id, String name, String organization) {
        this(id, name, 0, 0, organization);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
