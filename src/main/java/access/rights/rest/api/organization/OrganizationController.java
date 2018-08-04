package access.rights.rest.api.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/organizations")
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @RequestMapping("/organizations/{id}")
    public Organization getAllOrganizations(@PathVariable String id) {
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
}
