package ava.coding.challenge.main.organization.approval.request;

import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApprovalRequestController {

    @Autowired
    private ApprovalRequestService approvalRequestService;

    @RequestMapping("/master-organizations/approval-requests")
    public List<ApprovalRequest> getAllApprovalRequests() {
        return approvalRequestService.getAllApprovalRequests();
    }

    @RequestMapping("/master-organizations/approval-requests/{id}")
    public ApprovalRequest getApprovalRequest(@PathVariable String id) {
        return approvalRequestService.getApprovalRequest(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/master-organizations/approval-requests")
    public void addNewApprovalRequest(@RequestBody ApprovalRequest approvalRequest) {
        approvalRequestService.addApprovalRequest(approvalRequest);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/master-organizations/approval-requests")
    public void addNewApprovalRequestList(@RequestBody List<ApprovalRequest> approvalRequestList) {
        approvalRequestList.forEach(approvalRequestService::addApprovalRequest);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/master-organizations/approval-requests/{id}")
    public void updateApprovalRequest(@RequestBody ApprovalRequest updatedApprovalRequest, @PathVariable String id) {
        approvalRequestService.updateApprovalRequest(id, updatedApprovalRequest);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/master-organizations/approval-requests/{id}")
    public void deleteApprovalRequest(@PathVariable("id") String id) {
        approvalRequestService.deleteApprovalRequest(id);
    }

}
