package access.rights.rest.api.employee;

import access.rights.rest.api.organization.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ORGANIZATION_3')")
    @RequestMapping("/organizations/{organizationId}/employees")
    public List<Employee> getAllEmployees(@PathVariable String organizationId) {
        return employeeService.getAllEmployees(organizationId);
    }

    @RequestMapping("/organizations/{organizationId}/employees/{id}")
    public Employee getEmployee(@PathVariable("id") String id) {
        return employeeService.getEmployee(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations/{organizationId}/employees")
    public void addEmployee(@PathVariable String organizationId, @RequestBody Employee newEmployee) {
        newEmployee.setOrganization(new Organization(organizationId, ""));
        employeeService.addEmployee(newEmployee);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/organizations/{organizationId}/employees")
    public void addNewEmployeeList(@PathVariable String organizationId, @RequestBody List<Employee> employeeList) {
        employeeList.forEach(x -> x.setOrganization(new Organization(organizationId, "")));
        employeeList.forEach(employeeService::addEmployee);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/organizations/{organizationId}/employees/{id}")
    public void updateEmployee(@PathVariable String organizationId, @RequestBody Employee updatedEmployee, @PathVariable String id) {
        //TODO: is this needed?
        updatedEmployee.setOrganization(new Organization(organizationId, ""));
        employeeService.updateEmployee(id, updatedEmployee);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/organizations/{organizationId}/employees/{id}")
    public void deleteEmployee(@PathVariable("id") String id) {
        employeeService.deleteEmployee(id);
    }
}
