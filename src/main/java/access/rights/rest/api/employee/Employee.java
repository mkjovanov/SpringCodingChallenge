package access.rights.rest.api.employee;

import access.rights.rest.api.access.right.AccessRight;
import access.rights.rest.api.organization.Organization;
import access.rights.rest.api.repository.IEntity;

import java.util.Dictionary;

//@Entity
public class Employee implements IEntity {

    //@Id
    private String id;
    private String firstName;
    private String lastName;
    //@ManyToOne
    private Organization organization;
    private AccessRight internalAccessRight;
    private Dictionary<Organization, AccessRight> externalAccessRightList;

    public Employee(){ }
    public Employee(String id, String firstName, String lastName, String organizationId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = new Organization(organizationId, "<TO FIX>");
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
}
