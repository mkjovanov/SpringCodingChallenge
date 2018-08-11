package ava.coding.challenge.main.organization.repositories;

import ava.coding.challenge.main.organization.entities.Organization;
import ava.coding.challenge.repository.IRepository;
import org.springframework.stereotype.Repository;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganizationVoltDBRepository extends IRepository<Organization> {

    String driver = "org.voltdb.jdbc.Driver";
    String url = "avabackup.northeurope.cloudapp.azure.com:21212";
    Client client = null;
    ClientConfig config = null;

    public OrganizationVoltDBRepository() throws IOException {
        config = new ClientConfig("usr", "qlZ6rZT1");
        client = ClientFactory.createClient(config);
        client.createConnection(url);
    }

    @Override
    public List<Organization> getAll() {
        VoltTable voltDBOrganizationList;
        ArrayList<Organization> organizationList = new ArrayList<>();
        try {
            voltDBOrganizationList = client.callProcedure("getAllMasterOrganizations").getResults()[0];
            voltDBOrganizationList.resetRowPosition();
            while(voltDBOrganizationList.advanceRow()) {
                Organization o = new Organization();
                o.setId((String) voltDBOrganizationList.get("id", VoltType.STRING));
                o.setName((String) voltDBOrganizationList.get("name", VoltType.STRING));
                organizationList.add(o);
            }

            return organizationList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public Organization get(String id) {
        return null;
    }

    @Override
    public void add(Organization newEntity) {

    }

    @Override
    public void update(String id, Organization updatedEntity) {

    }

    @Override
    public void delete(String id) {

    }
}
