package access.rights.rest.api.organization;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrganizationService {

    private List<Organization> organizations =
            new ArrayList<>(Arrays.asList(
                new Organization(1, "A"),
                new Organization(2, "B"),
                new Organization(3, "C")
            ));

    public List<Organization> getAllOrganizations() {
        return organizations;
    }

    public Organization getOrganization(int id) {
        return organizations.stream().filter(o -> o.getId() == id).findFirst().get();
    }

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    public void updateOrganization(int id, Organization organization) {
        for (int i = 0; i < organizations.size(); i++) {
            Organization o = organizations.get(i);
            if(o.getId() == id) {
                organizations.set(i, organization);
                return;
            }
        }
    }

}
