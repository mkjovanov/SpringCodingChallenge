package ava.coding.challenge.main.organization.access.rights.entities.access.rights;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.access.rights.entities.QuantityRestriction;
import ava.coding.challenge.repository.IEntity;

import java.util.EnumSet;

public class ExternalAccessRights extends AccessRightsBase implements IEntity {

    private String id;
    private String receivingOrganization;
    private String givingOrganization;
    private QuantityRestriction quantityRestriction;

    public ExternalAccessRights() { }
    public ExternalAccessRights(String id, String receivingOrganization, String givingOrganization) {
        this(id, receivingOrganization, givingOrganization, EnumSet.of(CrudOperation.Read), null);
    }
    public ExternalAccessRights(String id, String receivingOrganization, String givingOrganization,
                                EnumSet<CrudOperation> crudOperationSet) {
        this(id, receivingOrganization, givingOrganization, crudOperationSet, null);
    }
    public ExternalAccessRights(String id, String receivingOrganization, String givingOrganization,
                                EnumSet<CrudOperation> crudOperationSet, QuantityRestriction quantityRestriction) {
        this.id = id;
        this.receivingOrganization = receivingOrganization;
        this.givingOrganization = givingOrganization;
        this.quantityRestriction = quantityRestriction;
        super.crudOperations = crudOperationSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceivingOrganization() {
        return receivingOrganization;
    }

    public void setReceivingOrganization(String receivingOrganization) {
        this.receivingOrganization = receivingOrganization;
    }

    public String getGivingOrganization() {
        return givingOrganization;
    }

    public void setGivingOrganization(String givingOrganization) {
        this.givingOrganization = givingOrganization;
    }

    public QuantityRestriction getQuantityRestriction() {
        return quantityRestriction;
    }

    public void setQuantityRestriction(QuantityRestriction quantityRestriction) {
        this.quantityRestriction = quantityRestriction;
    }
}
