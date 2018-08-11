package ava.coding.challenge.main.employee.entities;

import ava.coding.challenge.main.organization.access.rights.entities.access.rights.InternalAccessRights;
import ava.coding.challenge.repository.IEntity;

public class Employee implements IEntity {

    private String id;
    private String firstName;
    private String lastName;
    private String organization;
    private InternalAccessRights internalAccessRights;

    public Employee(){ }
    public Employee(String id, String firstName, String lastName, String organization,
                    InternalAccessRights internalAccessRights) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.internalAccessRights = internalAccessRights;
    }
    public Employee(String id, String firstName, String lastName, String organization) {
        this(id, firstName, lastName, organization, new InternalAccessRights());
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public InternalAccessRights getInternalAccessRights() {
        return internalAccessRights;
    }

    public void setInternalAccessRights(InternalAccessRights internalAccessRights) {
        this.internalAccessRights = internalAccessRights;
    }
}
