package ava.coding.challenge.main.organization.access.rights;

import ava.coding.challenge.main.organization.access.rights.entities.access.rights.ExternalAccessRights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExternalAccessRightsController {

    @Autowired
    private ExternalAccessRightsService externalAccessRightsService;

    @RequestMapping("/master-organizations/external-access-rights")
    public List<ExternalAccessRights> getAllExternalAccessRights() {
        return externalAccessRightsService.getAllExternalAccessRights();
    }

    @RequestMapping("/master-organizations/external-access-rights/{id}")
    public ExternalAccessRights getExternalAccessRights(@PathVariable String id) {
        return externalAccessRightsService.getExternalAccessRights(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/master-organizations/external-access-rights")
    public void addNewExternaAccesslRights(@RequestBody ExternalAccessRights externalAccessRights) {
        externalAccessRightsService.addExternalAccessRights(externalAccessRights);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/master-organizations/external-access-rights")
    public void addNewExternalAccessRightsList(@RequestBody List<ExternalAccessRights> externalAccessRightsList) {
        externalAccessRightsList.forEach(externalAccessRightsService::addExternalAccessRights);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/master-organizations/external-access-rights/{id}")
    public void updateExternalAccessRights(@RequestBody ExternalAccessRights updatedExternalAccessRights,
                                           @PathVariable String id) {
        externalAccessRightsService.updatedExternalAccessRights(id, updatedExternalAccessRights);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/master-organizations/external-access-rights/{id}")
    public void deleteExternalAccessRights(@PathVariable("id") String id) {
        externalAccessRightsService.deleteExternalAccessRights(id);
    }
}
