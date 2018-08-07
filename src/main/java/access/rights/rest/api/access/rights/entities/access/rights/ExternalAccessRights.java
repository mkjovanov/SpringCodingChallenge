package access.rights.rest.api.access.rights.entities.access.rights;

import access.rights.rest.api.access.rights.entities.CrudOperation;
import access.rights.rest.api.access.rights.entities.QuantityRestriction;

import java.util.EnumSet;

public class ExternalAccessRights extends AccessRightsBase {

    private QuantityRestriction quantityRestriction;

    public ExternalAccessRights() {
        this(EnumSet.of(CrudOperation.Read), null);
    }
    public ExternalAccessRights(EnumSet<CrudOperation> crudOperationSet) {
        this(crudOperationSet, null);
    }
    public ExternalAccessRights(EnumSet<CrudOperation> crudOperations, QuantityRestriction quantityRestriction) {
        super.crudOperations = crudOperations;
        this.quantityRestriction = quantityRestriction;
    }

    public QuantityRestriction getQuantityRestriction() {
        return quantityRestriction;
    }

    public void setQuantityRestriction(QuantityRestriction quantityRestriction) {
        this.quantityRestriction = quantityRestriction;
    }
}
