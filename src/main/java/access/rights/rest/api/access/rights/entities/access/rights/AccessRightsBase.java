package access.rights.rest.api.access.rights.entities.access.rights;

import access.rights.rest.api.access.rights.entities.CrudOperation;

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
