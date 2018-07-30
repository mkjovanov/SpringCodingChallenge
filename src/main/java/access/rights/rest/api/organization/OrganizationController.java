package access.rights.rest.api.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    public Organization getAllOrganizations(@PathVariable Integer id) {
        return organizationService.getOrganization(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations")
    public void addNewOrganization(@RequestBody Organization organization) {
        organizationService.addOrganization(organization);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/organizations/{id}")
    public void updateOrganization(@RequestBody Organization updatedOrganization) {
        organizationService.updateOrganization(updatedOrganization);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/organizations/{id}")
    public void deleteOrganization(@PathVariable("id") Integer id) {
        organizationService.deleteOrganization(id);
    }
}
