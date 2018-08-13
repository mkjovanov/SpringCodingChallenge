package ava.coding.challenge.main.organization.approval.request.entities;

import ava.coding.challenge.main.organization.access.rights.entities.access.rights.ExternalAccessRights;
import ava.coding.challenge.repository.IEntity;

public class ApprovalRequest implements IEntity {

    private String id;
    private String requestingOrganization;
    private RequestingRights requestingRights;

    public ApprovalRequest() {}
    public ApprovalRequest(String id, String requestingOrganization, RequestingRights requestingRights) {
        this.id = id;
        this.requestingOrganization = requestingOrganization;
        this.requestingRights = requestingRights;
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

    public RequestingRights getRequestingRights() {
        return requestingRights;
    }

    public void setRequestingRights(RequestingRights requestingRights) {
        this.requestingRights = requestingRights;
    }
}
