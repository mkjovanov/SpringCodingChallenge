package access.rights.rest.api.employee;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public final class Employee {

    @Id
    public Integer id;
    public String firstName;
    public String lastName;

    public Employee(){}
    public Employee(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
