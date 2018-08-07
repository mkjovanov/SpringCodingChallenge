package access.rights.rest.api.product.entities;

import access.rights.rest.api.organization.entities.Organization;
import access.rights.rest.api.repository.IEntity;

public class Product implements IEntity {

    private String id;
    private String name;
    private double price;
    private int stock;
    private Organization organization;

    public Product() {
        this("Access denied", "", 0, 0, "");
    }
    public Product(String id, String name, double price, int stock, String organizationId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.organization = new Organization(organizationId, "");
    }
    public Product(String id, String name) {
        this(id, name, 0, 0, "");
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
