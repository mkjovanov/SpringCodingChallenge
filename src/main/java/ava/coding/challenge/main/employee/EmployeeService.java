package ava.coding.challenge.main.employee;

import ava.coding.challenge.main.employee.entities.Employee;
import ava.coding.challenge.main.employee.repositories.EmployeeInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeInMemoryRepository employeeRepository;

    public List<Employee> getAllEmployees(String organizationId) {
        return employeeRepository.getAllByOrganizationId(organizationId);
    }

    public Employee getEmployee(String id) {
        return employeeRepository.get(id);
    }

    public void addEmployee(Employee newEmployee) {
        employeeRepository.add(newEmployee);
    }

    public void updateEmployee(String id, Employee updatedEmployee) {
        employeeRepository.update(id, updatedEmployee);
    }

    public void deleteEmployee(String id) {
        employeeRepository.delete(id);
    }
}
