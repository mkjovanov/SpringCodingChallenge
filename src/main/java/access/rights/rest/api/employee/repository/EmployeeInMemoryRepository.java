package access.rights.rest.api.employee.repository;

import access.rights.rest.api.employee.Employee;
import access.rights.rest.api.repository.IRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeInMemoryRepository extends IRepository <Employee> {

    public List<Employee> employeeList = new ArrayList<>(Arrays.asList(
            new Employee(1, "Pera", "Perić", 2),
            new Employee(2, "Mika", "Mikić", 2),
            new Employee(3, "Zora", "Zorić", 2),
            new Employee(4, "Sloba", "Stanković", 3),
            new Employee(5, "Jovana", "Jovanović", 3),
            new Employee(6, "Nemanja", "Nemanjić", 3)));

    @Override
    public List<Employee> getAll() {
        return employeeList;
    }

    public List<Employee> getAllByOrganizationId(Integer organizationId) {
        return employeeList.stream().filter(e -> e.getOrganization().getId() == organizationId).collect(Collectors.toList());
    }

    @Override
    public Employee get(Integer id) {
        return employeeList.stream().filter(e -> e.getId() == id).findFirst().get();
    }

    @Override
    public void add(Employee newEmployee) {
        employeeList.add(newEmployee);
    }

    @Override
    public void update(Employee updatedEmployee) {
        for(int i = 0; i < employeeList.size(); i++) {
            Employee e = employeeList.get(i);
            if(e.getId() == updatedEmployee.getId()) {
                employeeList.set(i, updatedEmployee);
                return;
            }
        }
    }

    @Override
    public void delete(Integer id) {
        employeeList.removeIf(e -> e.getId()  == id);
    }
}
