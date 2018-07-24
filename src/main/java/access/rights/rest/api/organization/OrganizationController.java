package access.rights.rest.api.organization;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class OrganizationController {

    @RequestMapping("/organization")
    public List<Organization> getAllOrganizations() {
        return Arrays.asList(
                new Organization(1, "A"),
                new Organization(2, "B"),
                new Organization(3, "C")
        );
    }
}
