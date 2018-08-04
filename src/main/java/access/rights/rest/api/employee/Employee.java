package access.rights.rest.api.employee;

import access.rights.rest.api.organization.Organization;
import access.rights.rest.api.repository.IEntity;

//@Entity
public class Employee implements IEntity {

    //@Id
    private Integer id;
    private String firstName;
    private String lastName;
    //@ManyToOne
    private Organization organization;

    public Employee(){ }
    public Employee(int id, String firstName, String lastName, Integer organizationId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = new Organization(organizationId, "<TO FIX>");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
