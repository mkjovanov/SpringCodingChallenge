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
import java.util.UUID;

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
            voltDBOrganizationList = client.callProcedure("getAllOrganizations").getResults()[0];
            voltDBOrganizationList.resetRowPosition();
            while(voltDBOrganizationList.advanceRow()) {
                Organization o = new Organization();
                o.setId((String) voltDBOrganizationList.get("OrganizationId", VoltType.STRING));
                o.setName((String) voltDBOrganizationList.get("Name", VoltType.STRING));
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
        VoltTable voltDBOrganization;
        Organization organization = null;
        try {
            voltDBOrganization = client.callProcedure("getOrganization", id).getResults()[0];
            voltDBOrganization.resetRowPosition();
            while(voltDBOrganization.advanceRow()) {
                //TODO: Set external rights
                organization = new Organization();
                organization.setId((String) voltDBOrganization.get("OrganizationId", VoltType.STRING));
                organization.setName((String) voltDBOrganization.get("Name", VoltType.STRING));
            }

            return organization;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void add(Organization newEntity) {
        try {
            if(newEntity.getId() == null) {
                newEntity.setId(UUID.randomUUID().toString());
            }
            client.callProcedure("ORGANIZATIONS.insert", newEntity.getId(), newEntity.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void update(String id, Organization updatedEntity) {
        try {
            client.callProcedure("ORGANIZATIONS.update", updatedEntity.getId(), updatedEntity.getName(), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void delete(String id) {
        try {
            client.callProcedure("ORGANIZATIONS.delete", id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }
}
