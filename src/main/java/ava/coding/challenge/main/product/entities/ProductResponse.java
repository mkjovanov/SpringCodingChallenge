package ava.coding.challenge.main.product.entities;

import ava.coding.challenge.main.organization.access.rights.entities.access.rights.InternalAccessRights;

public class ProductResponse {

    private Product product;
    private InternalAccessRights accessRights;

    public ProductResponse() {}
    public ProductResponse(Product product, InternalAccessRights accessRights) {
        this.product = product;
        this.accessRights = accessRights;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public InternalAccessRights getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(InternalAccessRights accessRights) {
        this.accessRights = accessRights;
    }
}
