package access.rights.rest.api.employee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class EmployeeController {

    @RequestMapping("/employee")
    public List<Employee> getAllEmployees() {
        return Arrays.asList(
                new Employee(1, "Pera", "Peric"),
                new Employee(2, "Mika", "Mikic"),
                new Employee(3, "Zora", "Zoric")
        );
    }
}
