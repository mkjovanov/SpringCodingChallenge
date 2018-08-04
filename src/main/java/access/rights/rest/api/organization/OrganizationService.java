package access.rights.rest.api.organization;

import access.rights.rest.api.organization.repository.OrganizationInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationInMemoryRepository organizationRepository;

    public List<Organization> getAllOrganizations() {
        return organizationRepository.getAll();
    }

    public Organization getOrganization(Integer id) {
        return organizationRepository.get(id);
    }

    public void addOrganization(Organization newOrganization) {
        organizationRepository.add(newOrganization);
    }

    public void updateOrganization(Organization updatedOrganization) {
        organizationRepository.update(updatedOrganization);
    }

    public void deleteOrganization(Integer id) {
        organizationRepository.delete(id);
    }
}
