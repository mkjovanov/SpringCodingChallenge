package access.rights.rest.api.organization.repository;

import access.rights.rest.api.organization.Organization;
import access.rights.rest.api.repository.IRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrganizationInMemoryRepository extends IRepository<Organization> {

    public List<Organization> organizationList = new ArrayList<>(Arrays.asList(
            new Organization(1, "A"),
            new Organization(2, "B"),
            new Organization(3, "C")));

    @Override
    public List<Organization> getAll() {
        return organizationList;
    }

    @Override
    public Organization get(Integer id) {
        return organizationList.stream().filter(e -> e.getId() == id).findFirst().get();
    }

    @Override
    public void add(Organization newOrganization) {
        organizationList.add(newOrganization);
    }

    @Override
    public void update(Organization updatedOrganization) {
        for(int i = 0; i < organizationList.size(); i++) {
            Organization e = organizationList.get(i);
            if(e.getId() == updatedOrganization.getId()) {
                organizationList.set(i, updatedOrganization);
                return;
            }
        }
    }

    @Override
    public void delete(Integer id) {
        organizationList.removeIf(e -> e.getId()  == id);
    }
}
