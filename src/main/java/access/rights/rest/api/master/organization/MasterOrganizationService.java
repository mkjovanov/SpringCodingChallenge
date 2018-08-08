package access.rights.rest.api.master.organization;

import access.rights.rest.api.master.organization.entities.MasterOrganization;
import access.rights.rest.api.master.organization.repositories.MasterOrganizationInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterOrganizationService {

    @Autowired
    private MasterOrganizationInMemoryRepository masterOrganizationInMemoryRepository;

    public List<MasterOrganization> getAllMasterOrganizations() {
        return masterOrganizationInMemoryRepository.getAll();
    }

    public MasterOrganization getMasterOrganization(String id) {
        return masterOrganizationInMemoryRepository.get(id);
    }

    public void addMasterOrganization(MasterOrganization newOrganization) {
        masterOrganizationInMemoryRepository.add(newOrganization);
    }

    public void updateMasterOrganization(String id, MasterOrganization updatedOrganization) {
        masterOrganizationInMemoryRepository.update(id, updatedOrganization);
    }

    public void deleteMasterOrganization(String id) {
        masterOrganizationInMemoryRepository.delete(id);
    }
}
