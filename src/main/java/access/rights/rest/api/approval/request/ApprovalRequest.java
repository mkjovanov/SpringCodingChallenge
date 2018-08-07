package access.rights.rest.api.approval.request;

import access.rights.rest.api.access.rights.AccessRights;

public class ApprovalRequest {

    private String id;
    private String requestingOrganization;
    private String receivingOrganization;
    private AccessRights accessRights;

    public ApprovalRequest(String id, String requestingOrganization, String receivingOrganization, AccessRights accessRights) {
        this.id = id;
        this.requestingOrganization = requestingOrganization;
        this.receivingOrganization = receivingOrganization;
        this.accessRights = accessRights;
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

    public String getReceivingOrganization() {
        return receivingOrganization;
    }

    public void setReceivingOrganization(String receivingOrganization) {
        this.receivingOrganization = receivingOrganization;
    }

    public AccessRights getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(AccessRights accessRights) {
        this.accessRights = accessRights;
    }
}
