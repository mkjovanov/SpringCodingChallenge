package ava.coding.challenge.main.employee.repositories;

import ava.coding.challenge.main.employee.entities.Employee;
import ava.coding.challenge.repository.IRepository;
import org.springframework.stereotype.Repository;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class EmployeeVoltDBRepository extends IRepository<Employee> {

    String driver = "org.voltdb.jdbc.Driver";
    String url = "avabackup.northeurope.cloudapp.azure.com:21212";
    Client client = null;
    ClientConfig config = null;

    public EmployeeVoltDBRepository() throws IOException {
        config = new ClientConfig("usr", "qlZ6rZT1");
        client = ClientFactory.createClient(config);
        client.createConnection(url);
    }

    @Override
    public List<Employee> getAll() {
        VoltTable voltDBOrganizationList;
        ArrayList<Employee> employeesList = new ArrayList<>();
        try {
            voltDBOrganizationList = client.callProcedure("getAllEmployees").getResults()[0];
            voltDBOrganizationList.resetRowPosition();

            while(voltDBOrganizationList.advanceRow()) {
                Employee initializedEmployee = initializeEmployee(voltDBOrganizationList, employeesList);
                employeesList.add(initializedEmployee);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
        return employeesList;
    }

    @Override
    public Employee get(String id) {
        VoltTable voltDBEmployee;
        Employee employee = null;
        try {
            voltDBEmployee = client.callProcedure("getEmployee", id).getResults()[0];
            voltDBEmployee.resetRowPosition();
            while(voltDBEmployee.advanceRow()) {
                employee = new Employee();
                employee.setId((String) voltDBEmployee.get("EmployeeId", VoltType.STRING));
                employee.setFirstName((String) voltDBEmployee.get("FirstName", VoltType.STRING));
                employee.setLastName((String) voltDBEmployee.get("LastName", VoltType.STRING));
                employee.setOrganization((String) voltDBEmployee.get("OrganizationId", VoltType.STRING));
                //TODO: Set internal access rights
                //employee.setInternalAccessRights((String) voltDBOrganizationList.get("OrganizationId", VoltType.STRING));
            }

            return employee;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void add(Employee newEntity) {
        try {
            if(newEntity.getId() == null) {
                newEntity.setId(UUID.randomUUID().toString());
            }
            // TODO: Map CRUD operations
            client.callProcedure("EMPLOYEES.insert",
                    newEntity.getId(), newEntity.getFirstName(), newEntity.getLastName(),
                    newEntity.getOrganization(), 0, 1, 0, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void update(String id, Employee updatedEntity) {
        try {
            // TODO: Map the function operations
            client.callProcedure("EMPLOYEES.update", updatedEntity.getId(), updatedEntity.getFirstName(), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void delete(String id) {
        try {
            client.callProcedure("EMPLOYEES.delete", id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    public List<Employee> getAllByOrganizationId(String organizationId) {
        VoltTable voltDBOrganizationList;
        ArrayList<Employee> employeesList = new ArrayList<>();

        try {
            voltDBOrganizationList = client.callProcedure("getAllEmployeesByOrganizationId", organizationId).getResults()[0];
            voltDBOrganizationList.resetRowPosition();

            while(voltDBOrganizationList.advanceRow()) {
                Employee initializedEmployee = initializeEmployee(voltDBOrganizationList, employeesList);
                employeesList.add(initializedEmployee);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }

        return employeesList;
    }

    private Employee initializeEmployee(VoltTable voltDBOrganizationList, ArrayList<Employee> employeesList) {
        Employee employee = new Employee();
        employee.setId((String) voltDBOrganizationList.get("EmployeeId", VoltType.STRING));
        employee.setFirstName((String) voltDBOrganizationList.get("FirstName", VoltType.STRING));
        employee.setLastName((String) voltDBOrganizationList.get("LastName", VoltType.STRING));
        employee.setOrganization((String) voltDBOrganizationList.get("OrganizationId", VoltType.STRING));
        //TODO: Set internal access rights
        //employee.setInternalAccessRights((String) voltDBOrganizationList.get("OrganizationId", VoltType.STRING));
        return employee;
    }
}
