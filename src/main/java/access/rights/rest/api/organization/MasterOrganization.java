package access.rights.rest.api.organization;

import access.rights.rest.api.approval.request.ApprovalRequest;

import java.util.List;

public class MasterOrganization extends Organization {

    private List<ApprovalRequest> pendingApprovalRequestList;

    public MasterOrganization(List<ApprovalRequest> pendingApprovalRequestList) {
        this.pendingApprovalRequestList = pendingApprovalRequestList;
    }

    public List<ApprovalRequest> getPendingApprovalRequestList() {
        return pendingApprovalRequestList;
    }

    public void setPendingApprovalRequestList(List<ApprovalRequest> pendingApprovalRequestList) {
        this.pendingApprovalRequestList = pendingApprovalRequestList;
    }
}

