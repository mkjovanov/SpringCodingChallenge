package access.rights.rest.api.employee.entities;

import access.rights.rest.api.access.rights.entities.access.rights.InternalAccessRights;
import access.rights.rest.api.organization.entities.Organization;
import access.rights.rest.api.repository.IEntity;

public class Employee implements IEntity {

    private String id;
    private String firstName;
    private String lastName;
    private Organization organization;
    private InternalAccessRights internalAccessRights;

    public Employee(){ }
    public Employee(String id, String firstName, String lastName, String organizationId,
                    InternalAccessRights internalAccessRights) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        //TODO: Change this?
        this.organization = new Organization(organizationId, "<TO FIX>");
        this.internalAccessRights = internalAccessRights;
    }
    public Employee(String id, String firstName, String lastName, String organizationId) {
        this(id, firstName, lastName, organizationId, new InternalAccessRights());
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

    public InternalAccessRights getInternalAccessRights() {
        return internalAccessRights;
    }

    public void setInternalExternalAccessRights(InternalAccessRights internalExternalAccessRights) {
        this.internalAccessRights = internalExternalAccessRights;
    }
}
