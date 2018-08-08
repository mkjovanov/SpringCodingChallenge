package access.rights.rest.api.access.rights.entities.access.rights;

import access.rights.rest.api.access.rights.entities.CrudOperation;
import access.rights.rest.api.access.rights.entities.QuantityRestriction;

import java.util.EnumSet;

public class ExternalAccessRights extends AccessRightsBase {

    private String sharedOrganization;
    private QuantityRestriction quantityRestriction;

    public ExternalAccessRights() { }
    public ExternalAccessRights(String sharedOrganization) {
        this(sharedOrganization, EnumSet.of(CrudOperation.Read), null);
    }
    public ExternalAccessRights(String sharedOrganization, EnumSet<CrudOperation> crudOperationSet) {
        this(sharedOrganization, crudOperationSet, null);
    }
    public ExternalAccessRights(String sharedOrganization, EnumSet<CrudOperation> crudOperationSet, QuantityRestriction quantityRestriction) {
        super.crudOperations = crudOperationSet;
        this.sharedOrganization = sharedOrganization;
        this.quantityRestriction = quantityRestriction;
    }

    public String getSharedOrganization() {
        return sharedOrganization;
    }

    public void setSharedOrganization(String sharedOrganization) {
        this.sharedOrganization = sharedOrganization;
    }

    public QuantityRestriction getQuantityRestriction() {
        return quantityRestriction;
    }

    public void setQuantityRestriction(QuantityRestriction quantityRestriction) {
        this.quantityRestriction = quantityRestriction;
    }
}
