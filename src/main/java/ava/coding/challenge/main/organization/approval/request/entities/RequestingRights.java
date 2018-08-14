package ava.coding.challenge.main.organization.approval.request.entities;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.access.rights.entities.QuantityRestriction;

import java.util.EnumSet;

public class RequestingRights {
    private String sharingOrganization;
    private EnumSet<CrudOperation> crudOperations;
    private QuantityRestriction quantityRestriction;

    public RequestingRights() {}
    public RequestingRights(String sharingOrganization, EnumSet<CrudOperation> crudOperations) {
        this(sharingOrganization, crudOperations, null);
    }
    public RequestingRights(String sharingOrganization, EnumSet<CrudOperation> crudOperations, QuantityRestriction quantityRestriction) {
        this.sharingOrganization = sharingOrganization;
        this.crudOperations = crudOperations;
        this.quantityRestriction = quantityRestriction;
    }

    public String getSharingOrganization() {
        return sharingOrganization;
    }

    public void setSharingOrganization(String sharingOrganization) {
        this.sharingOrganization = sharingOrganization;
    }

    public EnumSet<CrudOperation> getCrudOperations() {
        return crudOperations;
    }

    public void setCrudOperations(EnumSet<CrudOperation> crudOperations) {
        this.crudOperations = crudOperations;
    }

    public QuantityRestriction getQuantityRestriction() {
        return quantityRestriction;
    }

    public void setQuantityRestriction(QuantityRestriction quantityRestriction) {
        this.quantityRestriction = quantityRestriction;
    }
}
