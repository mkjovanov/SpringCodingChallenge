package access.rights.rest.api.product;

import access.rights.rest.api.access.rights.AccessRights;
import access.rights.rest.api.organization.Organization;
import access.rights.rest.api.repository.IEntity;

//@Entity
public class Product implements IEntity {

    //@Id
    private String id;
    private String name;
    private double price;
    private int stock;
    //@ManyToOne
    private Organization organization;
    //@ManyToOne
    private AccessRights accessRights;

    public Product() { }
    public Product(String id, String name, double price, int stock, String organizationId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.organization = new Organization(organizationId, "");
        this.accessRights = new AccessRights(true, true, true, true, null);
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

    public AccessRights getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(AccessRights accessRights) {
        this.accessRights = accessRights;
    }
}
