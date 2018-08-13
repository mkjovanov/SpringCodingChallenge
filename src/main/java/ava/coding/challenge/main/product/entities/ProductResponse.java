package ava.coding.challenge.main.product.entities;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;

import java.util.EnumSet;

public class ProductResponse {

    private Product product;
    private EnumSet<CrudOperation> accessRightsCrudOperations;

    public ProductResponse() {}
    public ProductResponse(Product product, EnumSet<CrudOperation> accessRightsCrudOperations) {
        this.product = product;
        this.accessRightsCrudOperations = accessRightsCrudOperations;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public EnumSet<CrudOperation> getAccessRightsCrudOperations() {
        return accessRightsCrudOperations;
    }

    public void setAccessRightsCrudOperations(EnumSet<CrudOperation> accessRightsCrudOperations) {
        this.accessRightsCrudOperations = accessRightsCrudOperations;
    }
}
