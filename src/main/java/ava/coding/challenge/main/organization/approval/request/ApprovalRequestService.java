package ava.coding.challenge.main.organization.approval.request;

import ava.coding.challenge.helpers.NullHelper;
import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
import ava.coding.challenge.main.organization.approval.request.repositories.ApprovalRequestInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalRequestService {

    @Autowired
    private ApprovalRequestInMemoryRepository approvalRequestInMemoryRepository;
    @Autowired
    private NullHelper nullHelper;

    public List<ApprovalRequest> getAllApprovalRequests() {
        return approvalRequestInMemoryRepository.getAll();
    }

    public ApprovalRequest getApprovalRequest(String id) {
        return approvalRequestInMemoryRepository.get(id);
    }

    public void addApprovalRequest(ApprovalRequest newApprovalRequest) {
        approvalRequestInMemoryRepository.add(newApprovalRequest);
    }

    public void updateApprovalRequest(String id, ApprovalRequest updatedApprovalRequest) {
        ApprovalRequest sanitizedApprovalRequest = nullHelper.sanitizeNullValues(id, updatedApprovalRequest);
        approvalRequestInMemoryRepository.update(id, sanitizedApprovalRequest);
    }

    public void deleteApprovalRequest(String id) {
        approvalRequestInMemoryRepository.delete(id);
    }
}
