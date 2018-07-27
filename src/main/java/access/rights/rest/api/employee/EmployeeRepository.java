package access.rights.rest.api.employee;

import access.rights.rest.api.organization.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    List<Employee> findByOrganizationId(Integer organizationId);
}
