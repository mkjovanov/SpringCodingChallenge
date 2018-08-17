package ava.coding.challenge.main.organization.repositories;

import ava.coding.challenge.main.organization.access.rights.ExternalAccessRightsService;
import ava.coding.challenge.main.organization.access.rights.entities.access.rights.ExternalAccessRights;
import ava.coding.challenge.main.organization.entities.MasterOrganization;
import ava.coding.challenge.main.organization.entities.Organization;
import ava.coding.challenge.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MasterOrganizationVoltDBRepository extends IRepository<MasterOrganization> {

    String url = "avabackup.northeurope.cloudapp.azure.com:21212";
    Client client = null;
    ClientConfig config = null;

    @Autowired
    ExternalAccessRightsService externalAccessRightsService;

    public MasterOrganizationVoltDBRepository() throws IOException {
        config = new ClientConfig("usr", "qlZ6rZT1");
        client = ClientFactory.createClient(config);
        client.createConnection(url);
    }

    @Override
    public List<MasterOrganization> getAll() {
        VoltTable voltDBMasterOrganizationList;
        ArrayList<MasterOrganization> organizationList = new ArrayList<>();
        try {
            voltDBMasterOrganizationList = client.callProcedure("getAllMasterOrganizations").getResults()[0];
            voltDBMasterOrganizationList.resetRowPosition();
            while(voltDBMasterOrganizationList.advanceRow()) {
                MasterOrganization masterOrganization = new MasterOrganization();
                masterOrganization.setId((String) voltDBMasterOrganizationList.get("MasterOrganizationId", VoltType.STRING));
                masterOrganization.setName((String) voltDBMasterOrganizationList.get("Name", VoltType.STRING));
                organizationList.add(masterOrganization);
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
    public MasterOrganization get(String id) {
        VoltTable voltDBMasterOrganization;
        MasterOrganization masterOrganization = null;
        try {
            voltDBMasterOrganization = client.callProcedure("getMasterOrganization", id).getResults()[0];
            voltDBMasterOrganization.resetRowPosition();
            while(voltDBMasterOrganization.advanceRow()) {
                masterOrganization = new MasterOrganization();
                masterOrganization.setId((String) voltDBMasterOrganization.get("MasterOrganizationId", VoltType.STRING));
                masterOrganization.setName((String) voltDBMasterOrganization.get("Name", VoltType.STRING));
            }

            return masterOrganization;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void add(MasterOrganization newEntity) {
        try {
            if(newEntity.getId() == null) {
                newEntity.setId(UUID.randomUUID().toString());
            }
            client.callProcedure("MASTERORGANIZATIONS.insert", newEntity.getId(), newEntity.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void update(String id, MasterOrganization updatedEntity) {
        try {
            client.callProcedure("MASTERORGANIZATIONS.update", updatedEntity.getId(), updatedEntity.getName(), id);
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
            client.callProcedure("MASTERORGANIZATIONS.delete", id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }
}
