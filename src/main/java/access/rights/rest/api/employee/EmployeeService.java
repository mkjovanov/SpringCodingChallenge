package access.rights.rest.api.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private List<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee(1, "Pera", "Peric"),
                new Employee(2, "Mika", "Mikic"),
                new Employee(3, "Zora", "Zoric"))
            );

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
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
