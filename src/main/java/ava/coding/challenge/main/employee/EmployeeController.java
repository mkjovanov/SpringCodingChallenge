package ava.coding.challenge.main.employee;

import ava.coding.challenge.main.employee.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/organizations/{organizationId}/employees")
    public List<Employee> getAllEmployeesByOrganization(@PathVariable String organizationId) {
        return employeeService.getAllEmployeesByOrganization(organizationId);
    }

    @RequestMapping("/organizations/{organizationId}/employees/{id}")
    public Employee getEmployee(@PathVariable("id") String id) {
        return employeeService.getEmployee(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations/{organizationId}/employees")
    @ResponseBody
    public ResponseEntity addEmployee(@PathVariable String organizationId,
                                      @RequestBody Employee newEmployee) {
        newEmployee.setOrganization(organizationId);
        return employeeService.addEmployee(newEmployee);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/organizations/{organizationId}/employees/{id}")
    public void updateEmployee(@PathVariable String organizationId,
                               @RequestBody Employee updatedEmployee,
                               @PathVariable String id) {
        employeeService.updateEmployee(id, updatedEmployee);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/organizations/{organizationId}/employees/{id}")
    @ResponseBody
    public ResponseEntity deleteEmployee(@PathVariable("organizationId") String organizationId,
                                         @PathVariable("id") String id) {
        return employeeService.deleteEmployee(organizationId, id);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/organizations/{organizationId}/employees")
    public void addNewEmployeeList(@PathVariable String organizationId,
                                   @RequestBody List<Employee> employeeList) {
        employeeList.forEach(e -> e.setOrganization(organizationId));
        employeeList.forEach(employeeService::addEmployee);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/organizations/employees")
    public ResponseEntity getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}
