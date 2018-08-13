package ava.coding.challenge.main.organization.approval.request.entities;

import ava.coding.challenge.main.organization.access.rights.entities.access.rights.ExternalAccessRights;
import ava.coding.challenge.repository.IEntity;

public class ApprovalRequest implements IEntity {

    private String id;
    private String requestingOrganization;
    private ExternalAccessRights externalAccessRights;

    public ApprovalRequest() {}
    public ApprovalRequest(String id, String requestingOrganization, ExternalAccessRights externalAccessRights) {
        this.id = id;
        this.requestingOrganization = requestingOrganization;
        this.externalAccessRights = externalAccessRights;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestingOrganization() {
        return requestingOrganization;
    }

    public void setRequestingOrganization(String requestingOrganization) {
        this.requestingOrganization = requestingOrganization;
    }

    public ExternalAccessRights getExternalAccessRights() {
        return externalAccessRights;
    }

    public void setExternalAccessRights(ExternalAccessRights externalAccessRights) {
        this.externalAccessRights = externalAccessRights;
    }
}