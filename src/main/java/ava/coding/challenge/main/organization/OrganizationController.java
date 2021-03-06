package ava.coding.challenge.main.organization;

import ava.coding.challenge.main.organization.approval.request.ApprovalRequestService;
import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
import ava.coding.challenge.main.organization.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ApprovalRequestService approvalRequestService;

    @RequestMapping("/organizations")
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @RequestMapping("/organizations/{id}")
    public Organization getOrganization(@PathVariable String id) {
        return organizationService.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations")
    public void addNewOrganization(@RequestBody Organization organization) {
        organizationService.addOrganization(organization);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/organizations")
    public void addNewOrganizationList(@RequestBody List<Organization> organizationList) {
        organizationList.forEach(organizationService::addOrganization);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/organizations/{id}")
    public void updateOrganization(@RequestBody Organization updatedOrganization, @PathVariable String id) {
        organizationService.updateOrganization(id, updatedOrganization);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/organizations/{id}")
    public void deleteOrganization(@PathVariable("id") String id) {
        organizationService.deleteOrganization(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations/{id}/requestApproval")
    @ResponseBody
    public ResponseEntity requestApproval(@PathVariable("id") String id, @RequestBody ApprovalRequest approvalRequest) {
        approvalRequest.setRequestingOrganization(id);
        approvalRequestService.addApprovalRequest(approvalRequest);
        return new ResponseEntity("Approval request successfully sent.", null, HttpStatus.ACCEPTED);
    }
}
