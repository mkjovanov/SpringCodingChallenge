package ava.coding.challenge.main.organization.access.rights.entities.repositories;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.access.rights.entities.QuantityRestriction;
import ava.coding.challenge.main.organization.access.rights.entities.RestrictingCondition;
import ava.coding.challenge.main.organization.access.rights.entities.access.rights.ExternalAccessRights;
import ava.coding.challenge.main.organization.approval.request.entities.RequestingRights;
import ava.coding.challenge.repository.IRepository;
import org.springframework.stereotype.Repository;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

@Repository
public class ExternalAccessRightsVoltDBRepository extends IRepository<ExternalAccessRights> {

    String url = "avabackup.northeurope.cloudapp.azure.com:21212";
    Client client = null;
    ClientConfig config = null;

    public ExternalAccessRightsVoltDBRepository() throws IOException {
        config = new ClientConfig("usr", "qlZ6rZT1");
        client = ClientFactory.createClient(config);
        client.createConnection(url);
    }

    @Override
    public List<ExternalAccessRights> getAll() {
        VoltTable voltDBExternalRightsList;
        ArrayList<ExternalAccessRights> externalRightsList = new ArrayList<>();
        try {
            voltDBExternalRightsList = client.callProcedure("getAllExternalRights").getResults()[0];
            voltDBExternalRightsList.resetRowPosition();
            while (voltDBExternalRightsList.advanceRow()) {
                ExternalAccessRights approvalRequest = initializeExternalAccessRights(voltDBExternalRightsList);
                externalRightsList.add(approvalRequest);
            }
            return externalRightsList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public ExternalAccessRights get(String id) {
        VoltTable voltDBExternalRights;
        try {
            voltDBExternalRights = client.callProcedure("getExternalRights", id).getResults()[0];
            voltDBExternalRights.resetRowPosition();
            ExternalAccessRights initializedExternalRights = null;
            while (voltDBExternalRights.advanceRow()) {
                initializedExternalRights = initializeExternalAccessRights(voltDBExternalRights);
            }
            return initializedExternalRights;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();

        }
    }

    public List<ExternalAccessRights> getByReceivingOrganization(String receivingOrganization) {
        VoltTable voltDBExternalRightsList;
        ArrayList<ExternalAccessRights> externalRightsList = new ArrayList<>();
        try {
            voltDBExternalRightsList = client.callProcedure(
                    "getExternalRightsByReceivingOrganization", receivingOrganization).getResults()[0];
            voltDBExternalRightsList.resetRowPosition();
            while (voltDBExternalRightsList.advanceRow()) {
                ExternalAccessRights approvalRequest = initializeExternalAccessRights(voltDBExternalRightsList);
                externalRightsList.add(approvalRequest);
            }
            return externalRightsList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void add(ExternalAccessRights newEntity) {
        try {
            if (newEntity.getId() == null) {
                newEntity.setId(UUID.randomUUID().toString());
            }
            QuantityRestriction quantityRestriction = newEntity.getQuantityRestriction();
            EnumSet<CrudOperation> crudOperations = newEntity.getCrudOperations();
            client.callProcedure("EXTERNALRIGHTS.insert",
                    newEntity.getId(),
                    newEntity.getReceivingOrganization(),
                    newEntity.getGivingOrganization(),
                    crudOperations.contains(CrudOperation.Create) == true ? 1 : 0,
                    crudOperations.contains(CrudOperation.Read) == true ? 1 : 0,
                    crudOperations.contains(CrudOperation.Update) == true ? 1 : 0,
                    crudOperations.contains(CrudOperation.Delete) == true ? 1 : 0,
                    quantityRestriction == null ? null : quantityRestriction.getRestrictedAmount(),
                    quantityRestriction == null ? null : String.valueOf(quantityRestriction.getRestrictingCondition()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void update(String id, ExternalAccessRights updatedEntity) {
        try {
            if (updatedEntity.getId() == null) {
                updatedEntity.setId(UUID.randomUUID().toString());
            }
            QuantityRestriction quantityRestriction = updatedEntity.getQuantityRestriction();
            EnumSet<CrudOperation> crudOperations = updatedEntity.getCrudOperations();
            client.callProcedure("EXTERNALRIGHTS.update",
                    updatedEntity.getId(),
                    updatedEntity.getReceivingOrganization(),
                    updatedEntity.getGivingOrganization(),
                    crudOperations.contains(CrudOperation.Create) == true ? 1 : 0,
                    crudOperations.contains(CrudOperation.Read) == true ? 1 : 0,
                    crudOperations.contains(CrudOperation.Update) == true ? 1 : 0,
                    crudOperations.contains(CrudOperation.Delete) == true ? 1 : 0,
                    quantityRestriction == null ? null : quantityRestriction.getRestrictedAmount(),
                    quantityRestriction == null ? null : String.valueOf(quantityRestriction.getRestrictingCondition()),
                    id);
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
            client.callProcedure("EXTERNALRIGHTS.delete", id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    public void deleteAll() {
        try {
            client.callProcedure("deleteAllExternalRights");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    private ExternalAccessRights initializeExternalAccessRights(VoltTable voltDBExternalAccessRights) {
        ExternalAccessRights externalAccessRights = new ExternalAccessRights();
        externalAccessRights.setId((String) voltDBExternalAccessRights.get("ExternalRightsId", VoltType.STRING));
        externalAccessRights.setReceivingOrganization((String) voltDBExternalAccessRights.get("ReceivingOrganizationId", VoltType.STRING));
        externalAccessRights.setGivingOrganization((String) voltDBExternalAccessRights.get("GivingOrganizationId", VoltType.STRING));

        EnumSet<CrudOperation> crudOperations = EnumSet.noneOf(CrudOperation.class);
        if((Byte) voltDBExternalAccessRights.get("IsCreate", VoltType.TINYINT) == 1 ? true : false){
            crudOperations.add(CrudOperation.Create);
        }
        if((Byte) voltDBExternalAccessRights.get("IsRead", VoltType.TINYINT) == 1 ? true : false) {
            crudOperations.add(CrudOperation.Read);
        }
        if((Byte) voltDBExternalAccessRights.get("IsUpdate", VoltType.TINYINT) == 1 ? true : false) {
            crudOperations.add(CrudOperation.Update);
        }
        if((Byte) voltDBExternalAccessRights.get("IsDelete", VoltType.TINYINT) == 1 ? true : false){
            crudOperations.add(CrudOperation.Delete);
        }
        externalAccessRights.setCrudOperations(crudOperations);

        Integer restrictingAmountDb = (Integer) voltDBExternalAccessRights.get("QuantityRestrictionAmount", VoltType.INTEGER);
        String restrictingConditionDb = (String) voltDBExternalAccessRights.get("RestrictingCondition", VoltType.STRING);
        if(restrictingAmountDb == null || restrictingConditionDb == null) {
            externalAccessRights.setQuantityRestriction(null);
            return externalAccessRights;
        }

        QuantityRestriction quantityRestriction = new QuantityRestriction();
        quantityRestriction.setRestrictedAmount(restrictingAmountDb);
        RestrictingCondition restrictingCondition = null;
        if(restrictingConditionDb != null && restrictingConditionDb.equals("LessThan")) {
            restrictingCondition = RestrictingCondition.LessThan;
        }
        else if(restrictingConditionDb != null && restrictingConditionDb.equals("GreaterThan")) {
            restrictingCondition = RestrictingCondition.GreaterThan;
        }

        if (restrictingAmountDb != null && restrictingConditionDb != null) {
            quantityRestriction.setRestrictingCondition(restrictingCondition);
        }
        externalAccessRights.setQuantityRestriction(quantityRestriction);

        return externalAccessRights;
    }
}
