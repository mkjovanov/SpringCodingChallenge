package ava.coding.challenge.main.organization.repositories;

import ava.coding.challenge.main.organization.entities.MasterOrganization;
import ava.coding.challenge.repository.IRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MasterOrganizationInMemoryRepository extends IRepository<MasterOrganization> {

    public List<MasterOrganization> masterOrganizationList = new ArrayList<>(Arrays.asList(
            new MasterOrganization("a", "A"),
            new MasterOrganization("b", "B"),
            new MasterOrganization("c", "C")));

    @Override
    public List<MasterOrganization> getAll() {
        return masterOrganizationList;
    }

    @Override
    public MasterOrganization get(String id) {
        return masterOrganizationList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
    }

    @Override
    public void add(MasterOrganization newMasterOrganization) {
        masterOrganizationList.add(newMasterOrganization);
    }

    @Override
    public void update(String id, MasterOrganization updatedMasterOrganization) {
        for(int i = 0; i < masterOrganizationList.size(); i++) {
            MasterOrganization o = masterOrganizationList.get(i);
            if(o.getId().equals(id)) {
                masterOrganizationList.set(i, updatedMasterOrganization);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        masterOrganizationList.removeIf(e -> e.getId().equals(id));
    }
}
