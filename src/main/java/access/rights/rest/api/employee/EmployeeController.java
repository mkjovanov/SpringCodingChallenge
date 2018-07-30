package access.rights.rest.api.employee;

import access.rights.rest.api.organization.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/organizations/{organizationId}/employees")
    public List<Employee> getAllEmployees(@PathVariable Integer organizationId) {
        return employeeService.getAllEmployees(organizationId);
    }

    @RequestMapping("/organizations/{organizationId}/employees/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {
        return employeeService.getEmployee(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations/{organizationId}/employees")
    public void addEmployee(@PathVariable Integer organizationId, @RequestBody Employee newEmployee) {
        newEmployee.setOrganization(new Organization(organizationId, ""));
        employeeService.addEmployee(newEmployee);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/organizations/{organizationId}/employees/{id}")
    public void updateEmployee(@PathVariable Integer organizationId, @RequestBody Employee updatedEmployee) {
        updatedEmployee.setOrganization(new Organization(organizationId, ""));
        employeeService.updateEmployee(updatedEmployee);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/organizations/{organizationId}/employees/{id}")
    public void deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.deleteEmployee(id);
    }
}
