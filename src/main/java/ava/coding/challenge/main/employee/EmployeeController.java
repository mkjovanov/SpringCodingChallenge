package ava.coding.challenge.main.employee;

import ava.coding.challenge.main.employee.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/organizations/{organizationId}/employees")
    public List<Employee> getAllEmployees(@PathVariable String organizationId) {
        return employeeService.getAllEmployees(organizationId);
    }

    @RequestMapping("/organizations/{organizationId}/employees/{id}")
    public Employee getEmployee(@PathVariable("id") String id) {
        return employeeService.getEmployee(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations/{organizationId}/employees")
    public void addEmployee(@PathVariable String organizationId,
                            @RequestBody Employee newEmployee) {
        newEmployee.setOrganization(organizationId);
        employeeService.addEmployee(newEmployee);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/organizations/{organizationId}/employees/{id}")
    public void updateEmployee(@PathVariable String organizationId,
                               @RequestBody Employee updatedEmployee,
                               @PathVariable String id) {
        if (updatedEmployee.getOrganization() == null) {
            updatedEmployee.setOrganization(organizationId);
        }
        employeeService.updateEmployee(id, updatedEmployee);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/organizations/{organizationId}/employees/{id}")
    public void deleteEmployee(@PathVariable("id") String id) {
        employeeService.deleteEmployee(id);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/organizations/{organizationId}/employees")
    public void addNewEmployeeList(@PathVariable String organizationId,
                                   @RequestBody List<Employee> employeeList) {
        employeeList.forEach(e -> e.setOrganization(organizationId));
        employeeList.forEach(employeeService::addEmployee);
    }
}
