package ava.coding.challenge.main.organization.entities;

import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
import ava.coding.challenge.repository.IEntity;

import java.util.List;

public class MasterOrganization implements IEntity {

    private String id;
    private String name;

    public MasterOrganization() {}
    public MasterOrganization(String id, String name, List<ApprovalRequest> pendingApprovalRequestList) {
        this.id = id;
        this.name = name;
    }

    public MasterOrganization(String id, String name) {
        this(id, name, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

