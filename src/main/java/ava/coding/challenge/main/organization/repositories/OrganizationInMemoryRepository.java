package ava.coding.challenge.main.organization.repositories;

import ava.coding.challenge.main.organization.access.rights.ExternalAccessRightsService;
import ava.coding.challenge.main.organization.entities.Organization;
import ava.coding.challenge.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrganizationInMemoryRepository extends IRepository<Organization> {

    @Autowired
    private ExternalAccessRightsService externalAccessRightsService;

    public List<Organization> organizationList = new ArrayList<>(Arrays.asList(
            new Organization("a", "A"),
            new Organization("b", "B"),
            new Organization("c", "C")));

    @Override
    public List<Organization> getAll() {
        return organizationList;
    }

    @Override
    public Organization get(String id) {
        return organizationList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
    }

    @Override
    public void add(Organization newOrganization) {
        organizationList.add(newOrganization);
    }

    @Override
    public void update(String id, Organization updatedOrganization) {
        for(int i = 0; i < organizationList.size(); i++) {
            Organization o = organizationList.get(i);
            if(o.getId().equals(id)) {
                organizationList.set(i, updatedOrganization);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        organizationList.removeIf(e -> e.getId().equals(id));
    }
}
