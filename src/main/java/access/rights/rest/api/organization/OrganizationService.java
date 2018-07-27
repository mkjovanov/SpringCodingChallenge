package access.rights.rest.api.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    private List<Organization> organizations =
            new ArrayList<>(Arrays.asList(
                new Organization(1, "A"),
                new Organization(2, "B"),
                new Organization(3, "C")
            ));

    public List<Organization> getAllOrganizations() {
        List<Organization> employees = new ArrayList<>();
        organizationRepository.findAll().forEach(employees::add);
        return employees;
    }

    public Organization getOrganization(Integer id) {
        return organizationRepository.findById(id).get();
    }

    public void addOrganization(Organization newEmployee) {
        organizationRepository.save(newEmployee);
    }

    public void updateOrganization(Organization updatedEmployee) {
        organizationRepository.save(updatedEmployee);
    }

    public void deleteOrganization(Integer id) {
        organizationRepository.deleteById(id);
    }
}
