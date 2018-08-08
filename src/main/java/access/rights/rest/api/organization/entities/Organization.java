package access.rights.rest.api.organization.entities;

import access.rights.rest.api.access.rights.entities.access.rights.ExternalAccessRights;
import access.rights.rest.api.repository.IEntity;

import java.util.ArrayList;
import java.util.List;

public class Organization implements IEntity {

    private String id;
    private String name;
    private List<ExternalAccessRights> externalAccessRightsList;

    public Organization() { }
    public Organization(String id, String name) {
        this.id = id;
        this.name = name;
        this.externalAccessRightsList = new ArrayList<>();
    }
    public Organization(String id, String name, ArrayList<ExternalAccessRights> externalAccessRightsList) {
        this.id = id;
        this.name = name;
        this.externalAccessRightsList = externalAccessRightsList;
    }

    public Organization(String id) {
        this(id, "");
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

    public List<ExternalAccessRights> getExternalAccessRightsList() {
        return externalAccessRightsList;
    }

    public void setExternalAccessRightsList(List<ExternalAccessRights> externalAccessRightsList) {
        this.externalAccessRightsList = externalAccessRightsList;
    }
}
