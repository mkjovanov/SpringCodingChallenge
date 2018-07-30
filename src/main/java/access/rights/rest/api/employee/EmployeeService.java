package access.rights.rest.api.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(Integer organizationId) {
        List<Employee> employees = new ArrayList<>();
        employeeRepository
                .findByOrganizationId(organizationId)
                .forEach(employees::add);
        return employees;
    }

    public Employee getEmployee(Integer id) {
        return employeeRepository.findById(id).get();
    }

    public void addEmployee(Employee newEmployee) {
        employeeRepository.save(newEmployee);
    }

    public void updateEmployee(Employee updatedEmployee) {
        employeeRepository.save(updatedEmployee);
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
}
