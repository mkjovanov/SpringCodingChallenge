package ava.coding.challenge.main.organization.approval.request;

import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
import ava.coding.challenge.main.organization.approval.request.repositories.ApprovalRequestInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalRequestService {

    @Autowired
    private ApprovalRequestInMemoryRepository approvalRequestInMemoryRepository;

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
        approvalRequestInMemoryRepository.update(id, updatedApprovalRequest);
    }

    public void deleteApprovalRequest(String id) {
        approvalRequestInMemoryRepository.delete(id);
    }
}
