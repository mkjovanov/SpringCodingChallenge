package ava.coding.challenge.main.organization.access.rights.entities.repositories;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.access.rights.entities.access.rights.ExternalAccessRights;
import ava.coding.challenge.repository.IRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

@Repository
public class ExternalAccessRightsInMemoryRepository extends IRepository<ExternalAccessRights> {

    public List<ExternalAccessRights> externalAccessRightsList = new ArrayList<>(Arrays.asList(
            new ExternalAccessRights("1", "b", "c", CrudOperation.Read),
            new ExternalAccessRights("2", "c", "b", CrudOperation.Delete),
            new ExternalAccessRights("3", "d", "b", CrudOperation.Read)));

    @Override
    public List<ExternalAccessRights> getAll() {
        return externalAccessRightsList;
    }

    @Override
    public ExternalAccessRights get(String id) {
        return externalAccessRightsList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
    }

    @Override
    public void add(ExternalAccessRights newEntity) {
        externalAccessRightsList.add(newEntity);
    }

    @Override
    public void update(String id, ExternalAccessRights updatedEntity) {
        for(int i = 0; i < externalAccessRightsList.size(); i++) {
            ExternalAccessRights ear = externalAccessRightsList.get(i);
            if(ear.getId().equals(id)) {
                externalAccessRightsList.set(i, updatedEntity);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        externalAccessRightsList.removeIf(e -> e.getId().equals(id));
    }
}
