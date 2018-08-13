package ava.coding.challenge.main.organization.approval.request.entities;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;

import java.util.EnumSet;

public class RequestingRights {
    private String sharingOrganization;
    private EnumSet<CrudOperation> crudOperations;

    public RequestingRights() {}
    public RequestingRights(String sharingOrganization, EnumSet<CrudOperation> crudOperations) {
        this.sharingOrganization = sharingOrganization;
        this.crudOperations = crudOperations;
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
}
