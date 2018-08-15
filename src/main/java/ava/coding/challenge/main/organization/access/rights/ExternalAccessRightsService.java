package ava.coding.challenge.main.organization.access.rights;

import ava.coding.challenge.helpers.NullHelper;
import ava.coding.challenge.main.organization.access.rights.entities.access.rights.ExternalAccessRights;
import ava.coding.challenge.main.organization.access.rights.entities.repositories.ExternalAccessRightsVoltDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalAccessRightsService {

    @Autowired
    private ExternalAccessRightsVoltDBRepository externalAccessRepository;
    @Autowired
    private NullHelper nullHelper;

    public List<ExternalAccessRights> getAllExternalAccessRights() {
        return externalAccessRepository.getAll();
    }

    public ExternalAccessRights getExternalAccessRights(String id) {
        return externalAccessRepository.get(id);
    }

    public void addExternalAccessRights(ExternalAccessRights newExternalAccessRights) {
        externalAccessRepository.add(newExternalAccessRights);
    }

    public void updatedExternalAccessRights(String id, ExternalAccessRights updatedExternalAccessRights) {
        //Organization sanitizedExternalAccessRights = nullHelper.sanitizeNullValues(id, updatedExternalAccessRights);
        externalAccessRepository.update(id, updatedExternalAccessRights);
    }

    public void deleteExternalAccessRights(String id) {
        externalAccessRepository.delete(id);
    }

    public void deleteAllExternalAccessRights() {
        externalAccessRepository.deleteAll();
    }

    public List<ExternalAccessRights> getExternalAccessRightsByAccessingOrganization(String receivingOrganization) {
        return externalAccessRepository
                .getByReceivingOrganization(receivingOrganization).stream().collect(Collectors.toList());
    }
}
