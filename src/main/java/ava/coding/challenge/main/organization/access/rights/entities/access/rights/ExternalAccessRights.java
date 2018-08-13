package ava.coding.challenge.main.organization.access.rights.entities.access.rights;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.access.rights.entities.QuantityRestriction;
import ava.coding.challenge.repository.IEntity;

import java.util.EnumSet;

public class ExternalAccessRights extends AccessRightsBase implements IEntity {

    private String id;
    private String accessingOrganization;
    private String sharingOrganization;
    private QuantityRestriction quantityRestriction;

    public ExternalAccessRights() { }
    public ExternalAccessRights(String id, String accessingOrganization, String sharingOrganization) {
        this(id, accessingOrganization, sharingOrganization, EnumSet.of(CrudOperation.Read), null);
    }
    public ExternalAccessRights(String id, String accessingOrganization, String sharingOrganization,
                                EnumSet<CrudOperation> crudOperationSet) {
        this(id, accessingOrganization, sharingOrganization, crudOperationSet, null);
    }
    public ExternalAccessRights(String id, String accessingOrganization, String sharingOrganization,
                                EnumSet<CrudOperation> crudOperationSet, QuantityRestriction quantityRestriction) {
        this.id = id;
        this.accessingOrganization = accessingOrganization;
        this.sharingOrganization = sharingOrganization;
        this.quantityRestriction = quantityRestriction;
        super.crudOperations = crudOperationSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessingOrganization() {
        return accessingOrganization;
    }

    public void setAccessingOrganization(String accessingOrganization) {
        this.accessingOrganization = accessingOrganization;
    }

    public String getSharingOrganization() {
        return sharingOrganization;
    }

    public void setSharingOrganization(String sharingOrganization) {
        this.sharingOrganization = sharingOrganization;
    }

    public QuantityRestriction getQuantityRestriction() {
        return quantityRestriction;
    }

    public void setQuantityRestriction(QuantityRestriction quantityRestriction) {
        this.quantityRestriction = quantityRestriction;
    }
}
