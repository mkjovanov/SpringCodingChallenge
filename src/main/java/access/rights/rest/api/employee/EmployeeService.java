package access.rights.rest.api.employee;

import access.rights.rest.api.employee.repository.EmployeeInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeInMemoryRepository employeeRepository;

    public List<Employee> getAllEmployees(Integer organizationId) {
        return employeeRepository.getAllByOrganizationId(organizationId);
    }

    public Employee getEmployee(Integer id) {
        return employeeRepository.get(id);
    }

    public void addEmployee(Employee newEmployee) {
        employeeRepository.add(newEmployee);
    }

    public void updateEmployee(Employee updatedEmployee) {
        employeeRepository.update(updatedEmployee);
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.delete(id);
    }
}
