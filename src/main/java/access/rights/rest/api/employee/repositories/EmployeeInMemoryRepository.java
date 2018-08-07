package access.rights.rest.api.employee.repositories;

import access.rights.rest.api.access.rights.entities.CrudOperation;
import access.rights.rest.api.access.rights.entities.access.rights.ExternalAccessRights;
import access.rights.rest.api.access.rights.entities.access.rights.InternalAccessRights;
import access.rights.rest.api.employee.entities.Employee;
import access.rights.rest.api.repository.IRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EmployeeInMemoryRepository extends IRepository <Employee> {

    public List<Employee> employeeList = new ArrayList<>(Arrays.asList(
            new Employee("pera.peric", "Pera", "Perić", "b"),
            new Employee("mika.mikic", "Mika", "Mikić", "b"),
            new Employee("zora.zoric", "Zora", "Zorić", "b"),
            new Employee("sloba.stankovic", "Sloba", "Stanković", "c"),
            new Employee("jovana.jovanovic", "Jovana", "Jovanović", "c"),
            new Employee("nemanja.nemanjic", "Nemanja", "Nemanjić", "c")));

    @Override
    public List<Employee> getAll() {
        return employeeList;
    }

    public List<Employee> getAllByOrganizationId(String organizationId) {
        return employeeList.stream().filter(e -> e.getOrganization().getId().equals(organizationId)).collect(Collectors.toList());
    }

    @Override
    public Employee get(String id) {
        return employeeList.stream().filter(e -> e.getId().equals(id)).findFirst().get();
    }

    @Override
    public void add(Employee newEmployee) {
        employeeList.add(newEmployee);
    }

    @Override
    public void update(String id, Employee updatedEmployee) {
        for(int i = 0; i < employeeList.size(); i++) {
            Employee e = employeeList.get(i);
            if(e.getId().equals(id)) {
                employeeList.set(i, updatedEmployee);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        employeeList.removeIf(e -> e.getId().equals(id));
    }
}
