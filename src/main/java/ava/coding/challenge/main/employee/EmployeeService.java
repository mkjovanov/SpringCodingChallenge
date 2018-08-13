package ava.coding.challenge.main.employee;

import ava.coding.challenge.helpers.NullHelper;
import ava.coding.challenge.main.employee.entities.Employee;
import ava.coding.challenge.main.employee.repositories.EmployeeVoltDBRepository;
import ava.coding.challenge.main.organization.repositories.OrganizationVoltDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeVoltDBRepository employeeRepository;
    @Autowired
    private OrganizationVoltDBRepository organizationRepository;
    @Autowired
    private NullHelper nullHelper;

    public List<Employee> getAllEmployees(String organizationId) {
        return employeeRepository.getAllByOrganizationId(organizationId);
    }

    public Employee getEmployee(String id) {
        return employeeRepository.get(id);
    }

    public ResponseEntity addEmployee(Employee newEmployee) {
        if (organizationRepository.get(newEmployee.getOrganization()) == null) {
            return new ResponseEntity("Organization with id: '" + newEmployee.getOrganization() +
                                            "' doesn't exist!", HttpStatus.BAD_REQUEST);
        }

        employeeRepository.add(newEmployee);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    public void updateEmployee(String id, Employee updatedEmployee) {
        Employee sanitizedEmployee = nullHelper.sanitizeNullValues(id, updatedEmployee);
        employeeRepository.update(id, sanitizedEmployee);
    }

    public ResponseEntity deleteEmployee(String organizationId, String id) {
        if (organizationRepository.get(organizationId) == null) {
            return new ResponseEntity("Organization with id: '" + organizationId +
                                            "' doesn't exist!", HttpStatus.BAD_REQUEST);
        }
        else if (employeeRepository.get(id) == null) {
            return new ResponseEntity("Employee with id: '" + id +
                    "' doesn't exist!", HttpStatus.BAD_REQUEST);
        }
        else if (!employeeRepository.get(id).getOrganization().equals(organizationId)) {
            return new ResponseEntity("Employee cannot be deleted from different Organization URL", HttpStatus.FORBIDDEN);
        }
        employeeRepository.delete(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
