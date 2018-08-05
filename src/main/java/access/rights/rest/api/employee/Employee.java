package access.rights.rest.api.employee;

import access.rights.rest.api.access.rights.AccessRights;
import access.rights.rest.api.organization.Organization;
import access.rights.rest.api.repository.IEntity;
import org.aspectj.weaver.ast.Or;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//@Entity
public class Employee implements IEntity {

    //@Id
    private String id;
    private String firstName;
    private String lastName;
    //@ManyToOne
    private Organization organization;
    private AccessRights internalAccessRights;
    private Map<String, AccessRights> externalAccessRightsList;

    public Employee(){ }
    public Employee(String id, String firstName, String lastName, String organizationId,
                    AccessRights internalAccessRights, Map<String, AccessRights> externalAccessRightsList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        //TODO: Change this?
        this.organization = new Organization(organizationId, "<TO FIX>");
        this.internalAccessRights = internalAccessRights;
        this.externalAccessRightsList = externalAccessRightsList;
    }
    public Employee(String id, String firstName, String lastName, String organizationId) {
        this(id, firstName, lastName, organizationId, new AccessRights(), Collections.emptyMap());
    }
    public Employee(String id, String firstName, String lastName, String organizationId, AccessRights internalAccessRights) {
        this(id, firstName, lastName, organizationId, internalAccessRights, Collections.emptyMap());
    }
    public Employee(String id, String firstName, String lastName, String organizationId, Map<String, AccessRights> externalAccessRightsList) {
        this(id, firstName, lastName, organizationId, new AccessRights(), externalAccessRightsList);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public AccessRights getInternalAccessRights() {
        return internalAccessRights;
    }

    public void setInternalAccessRights(AccessRights internalAccessRights) {
        this.internalAccessRights = internalAccessRights;
    }

    public Map<String, AccessRights> getExternalAccessRightsList() {
        return externalAccessRightsList;
    }

    public void setExternalAccessRightsList(Map<String, AccessRights> externalAccessRightsList) {
        this.externalAccessRightsList = externalAccessRightsList;
    }
}
