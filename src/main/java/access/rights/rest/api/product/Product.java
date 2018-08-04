package access.rights.rest.api.product;

import access.rights.rest.api.access.right.AccessRight;
import access.rights.rest.api.organization.Organization;
import access.rights.rest.api.repository.IEntity;

//@Entity
public class Product implements IEntity {

    //@Id
    private Integer id;
    private String name;
    private double price;
    private int stock;
    //@ManyToOne
    private Organization organization;
    //@ManyToOne
    private AccessRight accessRight;

    public Product() { }
    public Product(Integer id, String name, double price, int stock, int organizationId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.organization = new Organization(organizationId, "");
        this.accessRight = new AccessRight(true, true, true, true);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }
}
