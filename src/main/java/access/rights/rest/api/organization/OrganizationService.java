package access.rights.rest.api.organization;

import access.rights.rest.api.organization.entities.Organization;
import access.rights.rest.api.organization.repositories.OrganizationInMemoryRepository;
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

    public Organization getOrganization(String id) {
        return organizationRepository.get(id);
    }

    public void addOrganization(Organization newOrganization) {
        organizationRepository.add(newOrganization);
    }

    public void updateOrganization(String id, Organization updatedOrganization) {
        organizationRepository.update(id, updatedOrganization);
    }

    public void deleteOrganization(String id) {
        organizationRepository.delete(id);
    }
}
