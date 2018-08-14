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
import java.util.EnumSet;
import java.util.List;

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
        return null;
    }

    @Override
    public ExternalAccessRights get(String id) {
        return null;
    }

    @Override
    public void add(ExternalAccessRights newEntity) {

    }

    @Override
    public void update(String id, ExternalAccessRights updatedEntity) {

    }

    @Override
    public void delete(String id) {

    }

    private ExternalAccessRights initializeExternalAccessrRights(VoltTable voltDBApprovalRequest) {
        ExternalAccessRights externalAccessRights = new ExternalAccessRights();

        externalAccessRights.setId((String) voltDBApprovalRequest.get("ApprovalRequestId", VoltType.STRING));
        externalAccessRights.setReceivingOrganization((String) voltDBApprovalRequest.get("ReceivingOrganization", VoltType.STRING));
        externalAccessRights.setGivingOrganization((String) voltDBApprovalRequest.get("GivingOrganization", VoltType.STRING));

        RequestingRights requestingRights = new RequestingRights();
        requestingRights.setSharingOrganization((String) voltDBApprovalRequest.get("SharingOrganizationId", VoltType.STRING));
        EnumSet<CrudOperation> crudOperations = EnumSet.noneOf(CrudOperation.class);
        if((Byte) voltDBApprovalRequest.get("IsCreate", VoltType.TINYINT) == 1 ? true : false){
            crudOperations.add(CrudOperation.Create);
        }
        if((Byte) voltDBApprovalRequest.get("IsRead", VoltType.TINYINT) == 1 ? true : false) {
            crudOperations.add(CrudOperation.Read);
        }
        if((Byte) voltDBApprovalRequest.get("IsUpdate", VoltType.TINYINT) == 1 ? true : false) {
            crudOperations.add(CrudOperation.Update);
        }
        if((Byte) voltDBApprovalRequest.get("IsDelete", VoltType.TINYINT) == 1 ? true : false){
            crudOperations.add(CrudOperation.Delete);
        }
        requestingRights.setCrudOperations(crudOperations);

        QuantityRestriction quantityRestriction = new QuantityRestriction();
        Integer restrictingAmountDb = (Integer) voltDBApprovalRequest.get("QuantityRestrictionAmount", VoltType.INTEGER);
        String restrictinConditionDb = (String) voltDBApprovalRequest.get("RestrictingCondition", VoltType.STRING);

        if(restrictingAmountDb == null || restrictinConditionDb == null) {
            requestingRights.setQuantityRestriction(null);
            //externalAccessRights.setRequestingRights(requestingRights);
            return externalAccessRights;
        }

        quantityRestriction.setRestrictedAmount(restrictingAmountDb);
        RestrictingCondition restrictingCondition = null;
        if(restrictinConditionDb != null && restrictinConditionDb.equals("LessThan")) {
            restrictingCondition = RestrictingCondition.LessThan;
        }
        else if(restrictinConditionDb != null && restrictinConditionDb.equals("GreaterThan")) {
            restrictingCondition = RestrictingCondition.GreaterThan;
        }

        if (restrictingAmountDb != null && restrictinConditionDb != null) {
            quantityRestriction.setRestrictingCondition(restrictingCondition);
            requestingRights.setQuantityRestriction(quantityRestriction);
        }

        //externalAccessRights.setRequestingRights(requestingRights);

        return externalAccessRights;
    }
}
