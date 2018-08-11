package ava.coding.challenge.main.organization.access.rights.entities.access.rights;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;

import java.util.EnumSet;

public class AccessRightsBase {

    protected EnumSet<CrudOperation> crudOperations;

    public AccessRightsBase() {
        this(EnumSet.of(CrudOperation.Read));
    }
    public AccessRightsBase(EnumSet<CrudOperation> crudOperationSet) {
        this.crudOperations = crudOperationSet;
    }

    public EnumSet<CrudOperation> getCrudOperations() {
        return crudOperations;
    }

    public void setCrudOperations(EnumSet<CrudOperation> crudOperationSet) {
        this.crudOperations = crudOperationSet;
    }
}
