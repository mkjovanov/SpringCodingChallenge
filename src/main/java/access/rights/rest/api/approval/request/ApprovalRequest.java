package access.rights.rest.api.approval.request;

import access.rights.rest.api.access.rights.entities.access.rights.ExternalAccessRights;

public class ApprovalRequest {

    private String id;
    private String requestingOrganization;
    private String receivingOrganization;
    private ExternalAccessRights externalAccessRights;

    public ApprovalRequest() {}
    public ApprovalRequest(String id, String requestingOrganization, String receivingOrganization, ExternalAccessRights externalAccessRights) {
        this.id = id;
        this.requestingOrganization = requestingOrganization;
        this.receivingOrganization = receivingOrganization;
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

    public String getReceivingOrganization() {
        return receivingOrganization;
    }

    public void setReceivingOrganization(String receivingOrganization) {
        this.receivingOrganization = receivingOrganization;
    }

    public ExternalAccessRights getExternalAccessRights() {
        return externalAccessRights;
    }

    public void setExternalAccessRights(ExternalAccessRights externalAccessRights) {
        this.externalAccessRights = externalAccessRights;
    }
}
