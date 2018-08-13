package ava.coding.challenge.main.organization.approval.request.repositories;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
import ava.coding.challenge.main.organization.approval.request.entities.RequestingRights;
import ava.coding.challenge.repository.IRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

@Repository
public class ApprovalRequestInMemoryRepository extends IRepository<ApprovalRequest> {

    public List<ApprovalRequest> approvalRequestList = new ArrayList<>(Arrays.asList(
            new ApprovalRequest("1", "c", new RequestingRights("b", EnumSet.of(CrudOperation.Create))),
            new ApprovalRequest("2", "c", new RequestingRights("b", EnumSet.of(CrudOperation.Delete))),
            new ApprovalRequest("2", "c", new RequestingRights("b", EnumSet.of(CrudOperation.Update)))));

    @Override
    public List<ApprovalRequest> getAll() {
        return approvalRequestList;
    }

    @Override
    public ApprovalRequest get(String id) {
        return approvalRequestList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
    }

    @Override
    public void add(ApprovalRequest newApprovalRequest) {
        approvalRequestList.add(newApprovalRequest);
    }

    @Override
    public void update(String id, ApprovalRequest updatedApprovalRequest) {
        for(int i = 0; i < approvalRequestList.size(); i++) {
            ApprovalRequest ar = approvalRequestList.get(i);
            if(ar.getId().equals(id)) {
                approvalRequestList.set(i, updatedApprovalRequest);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        approvalRequestList.removeIf(e -> e.getId().equals(id));
    }
}
