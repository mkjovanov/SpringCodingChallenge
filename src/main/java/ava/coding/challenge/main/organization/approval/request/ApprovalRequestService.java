package ava.coding.challenge.main.organization.approval.request;

import ava.coding.challenge.helpers.NullHelper;
import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
import ava.coding.challenge.main.organization.approval.request.repositories.ApprovalRequestVoltDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalRequestService {

    @Autowired
    private ApprovalRequestVoltDBRepository approvalRequestRepository;
    @Autowired
    private NullHelper nullHelper;

    public List<ApprovalRequest> getAllApprovalRequests() {
        return approvalRequestRepository.getAll();
    }

    public ApprovalRequest getApprovalRequest(String id) {
        return approvalRequestRepository.get(id);
    }

    public void addApprovalRequest(ApprovalRequest newApprovalRequest) {
        approvalRequestRepository.add(newApprovalRequest);
    }

    public void updateApprovalRequest(String id, ApprovalRequest updatedApprovalRequest) {
        ApprovalRequest sanitizedApprovalRequest = nullHelper.sanitizeNullValues(id, updatedApprovalRequest);
        approvalRequestRepository.update(id, sanitizedApprovalRequest);
    }

    public void deleteApprovalRequest(String id) {
        approvalRequestRepository.delete(id);
    }
}
