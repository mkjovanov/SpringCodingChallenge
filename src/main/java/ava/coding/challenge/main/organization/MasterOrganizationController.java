package ava.coding.challenge.main.organization;

import ava.coding.challenge.main.organization.access.rights.AccessRightsService;
import ava.coding.challenge.main.organization.entities.MasterOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MasterOrganizationController {

    @Autowired
    private MasterOrganizationService masterOrganizationService;
    @Autowired
    private AccessRightsService accessRightsService;

    @RequestMapping("/master-organizations")
    public List<MasterOrganization> getAllMasterOrganizations() {
        return masterOrganizationService.getAllMasterOrganizations();
    }

    @RequestMapping("/master-organizations/{id}")
    public MasterOrganization getMasterOrganization(@PathVariable String id) {
        return masterOrganizationService.getMasterOrganization(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/master-organizations")
    public void addNewMasterOrganization(@RequestBody MasterOrganization masterOrganization) {
        masterOrganizationService.addMasterOrganization(masterOrganization);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/master-organizations")
    public void addNewMasterOrganizationList(@RequestBody List<MasterOrganization> masterOrganizationList) {
        masterOrganizationList.forEach(masterOrganizationService::addMasterOrganization);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/master-organizations/{id}")
    public void updateMasterOrganization(@RequestBody MasterOrganization updatedMasterOrganization, @PathVariable String id) {
        masterOrganizationService.updateMasterOrganization(id, updatedMasterOrganization);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/master-organizations/{id}")
    public void deleteMasterOrganization(@PathVariable("id") String id) {
        masterOrganizationService.deleteMasterOrganization(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/master-organizations/approveRequest/{id}")
    public void approveRequest(@PathVariable("id") String id) {
        accessRightsService.approveRequest(id);
    }
}
