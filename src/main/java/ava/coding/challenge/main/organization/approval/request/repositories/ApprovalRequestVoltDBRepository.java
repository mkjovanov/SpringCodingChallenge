package ava.coding.challenge.main.organization.approval.request.repositories;

import ava.coding.challenge.main.employee.entities.Employee;
import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.access.rights.entities.QuantityRestriction;
import ava.coding.challenge.main.organization.access.rights.entities.RestrictingCondition;
import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
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
public class ApprovalRequestVoltDBRepository extends IRepository<ApprovalRequest> {

    String url = "avabackup.northeurope.cloudapp.azure.com:21212";
    Client client = null;
    ClientConfig config = null;

    public ApprovalRequestVoltDBRepository() throws IOException {
        config = new ClientConfig("usr", "qlZ6rZT1");
        client = ClientFactory.createClient(config);
        client.createConnection(url);
    }

    @Override
    public List<ApprovalRequest> getAll() {
        VoltTable voltDBApprovalRequestList;
        ArrayList<ApprovalRequest> approvalRequestList = new ArrayList<>();
        try {
            voltDBApprovalRequestList = client.callProcedure("getAllApprovalRequests").getResults()[0];
            voltDBApprovalRequestList.resetRowPosition();
            while (voltDBApprovalRequestList.advanceRow()) {
                ApprovalRequest approvalRequest = initializeApprovalRequest(voltDBApprovalRequestList);
                approvalRequestList.add(approvalRequest);
            }
            return approvalRequestList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public ApprovalRequest get(String id) {
        VoltTable voltDBApprovalRequest;
        Employee employee = null;

        try {
            voltDBApprovalRequest = client.callProcedure("getApprovalRequest", id).getResults()[0];
            voltDBApprovalRequest.resetRowPosition();
            ApprovalRequest initializedApprovalRequest = null;
            while (voltDBApprovalRequest.advanceRow()) {
                initializedApprovalRequest = initializeApprovalRequest(voltDBApprovalRequest);
            }
            return initializedApprovalRequest;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();

        }
    }

    @Override
    public void add(ApprovalRequest newEntity) {
        try {
            if (newEntity.getId() == null) {
                newEntity.setId(UUID.randomUUID().toString());
            }
            RequestingRights requestingRights = newEntity.getRequestingRights();
            QuantityRestriction quantityRestriction = requestingRights.getQuantityRestriction();
            client.callProcedure("APPROVALREQUESTS.upsert",
                                newEntity.getId(),
                                newEntity.getRequestingOrganization(),
                                requestingRights.getSharingOrganization(),
                                requestingRights.getCrudOperations().contains(CrudOperation.Create) == true ? 1 : 0,
                                requestingRights.getCrudOperations().contains(CrudOperation.Read) == true ? 1 : 0,
                                requestingRights.getCrudOperations().contains(CrudOperation.Update) == true ? 1 : 0,
                                requestingRights.getCrudOperations().contains(CrudOperation.Delete) == true ? 1 : 0,
                                quantityRestriction == null ? 0 : String.valueOf(quantityRestriction.getRestrictingCondition()),
                                quantityRestriction == null ? "0" : quantityRestriction.getRestrictedAmount());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void update(String id, ApprovalRequest updatedEntity) {
        try {
            if (updatedEntity.getId() == null) {
                updatedEntity.setId(UUID.randomUUID().toString());
            }
            RequestingRights requestingRights = updatedEntity.getRequestingRights();
            client.callProcedure("APPROVALREQUESTS.update",
                    updatedEntity.getId(),
                    updatedEntity.getRequestingOrganization(),
                    requestingRights.getSharingOrganization(),
                    requestingRights.getCrudOperations().contains(CrudOperation.Create) == true ? 1 : 0,
                    requestingRights.getCrudOperations().contains(CrudOperation.Read) == true ? 1 : 0,
                    requestingRights.getCrudOperations().contains(CrudOperation.Update) == true ? 1 : 0,
                    requestingRights.getCrudOperations().contains(CrudOperation.Delete) == true ? 1 : 0,
                    requestingRights.getQuantityRestriction().getRestrictedAmount(),
                    String.valueOf(requestingRights.getQuantityRestriction().getRestrictingCondition()),
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
        ApprovalRequest approvalRequest = get(id);
        QuantityRestriction quantityRestriction = approvalRequest.getRequestingRights().getQuantityRestriction();
        try {
            client.callProcedure("APPROVALREQUESTS.delete",
                                    approvalRequest.getRequestingOrganization(),
                                    approvalRequest.getRequestingRights().getSharingOrganization(),
                                    approvalRequest.getRequestingRights().getCrudOperations().contains(CrudOperation.Create) == true ? 1 : 0,
                                    approvalRequest.getRequestingRights().getCrudOperations().contains(CrudOperation.Read) == true ? 1 : 0,
                                    approvalRequest.getRequestingRights().getCrudOperations().contains(CrudOperation.Update) == true ? 1 : 0,
                                    approvalRequest.getRequestingRights().getCrudOperations().contains(CrudOperation.Delete) == true ? 1 : 0,
                                    quantityRestriction == null ? "0" : approvalRequest.getRequestingRights().getQuantityRestriction().getRestrictingCondition());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    public void deleteAll() {
        try {
            client.callProcedure("deleteAllApprovalRequests");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    private ApprovalRequest initializeApprovalRequest(VoltTable voltDBApprovalRequest) {
        ApprovalRequest approvalRequest = new ApprovalRequest();

        approvalRequest.setId((String) voltDBApprovalRequest.get("ApprovalRequestId", VoltType.STRING));
        approvalRequest.setRequestingOrganization((String) voltDBApprovalRequest.get("RequestingOrganizationId", VoltType.STRING));

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

        if(restrictingAmountDb == 0 || restrictinConditionDb == "") {
            requestingRights.setQuantityRestriction(null);
            approvalRequest.setRequestingRights(requestingRights);
            return approvalRequest;
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

        approvalRequest.setRequestingRights(requestingRights);

        return approvalRequest;
    }
}
