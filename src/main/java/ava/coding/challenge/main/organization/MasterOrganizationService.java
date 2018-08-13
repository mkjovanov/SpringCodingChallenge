package ava.coding.challenge.main.organization;

import ava.coding.challenge.helpers.NullHelper;
import ava.coding.challenge.main.organization.entities.MasterOrganization;
import ava.coding.challenge.main.organization.repositories.MasterOrganizationInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterOrganizationService {

    @Autowired
    private MasterOrganizationInMemoryRepository masterOrganizationInMemoryRepository;
    @Autowired
    private NullHelper nullHelper;

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
        MasterOrganization sanitizedMasterOrganization = nullHelper.sanitizeNullValues(id, updatedOrganization);
        masterOrganizationInMemoryRepository.update(id, sanitizedMasterOrganization);
    }

    public void deleteMasterOrganization(String id) {
        masterOrganizationInMemoryRepository.delete(id);
    }
}
