package ava.coding.challenge.main.employee.repositories;

import ava.coding.challenge.main.employee.entities.Employee;
import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.access.rights.entities.access.rights.InternalAccessRights;
import ava.coding.challenge.repository.IRepository;
import org.springframework.stereotype.Repository;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
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
        VoltTable voltDBEmployeeList;
        ArrayList<Employee> employeesList = new ArrayList<>();
        try {
            voltDBEmployeeList = client.callProcedure("getAllEmployees").getResults()[0];
            voltDBEmployeeList.resetRowPosition();

            while (voltDBEmployeeList.advanceRow()) {
                Employee initializedEmployee = initializeEmployee(voltDBEmployeeList);
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
            Employee initializedEmployee = null;

            while (voltDBEmployee.advanceRow()) {
                initializedEmployee = initializeEmployee(voltDBEmployee);
            }

            return initializedEmployee;
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
            if(newEntity.getInternalAccessRights() == null) {
                newEntity.setInternalAccessRights(new InternalAccessRights());
            }

            client.callProcedure("EMPLOYEES.insert",
                                     newEntity.getId(),
                                     newEntity.getFirstName(),
                                     newEntity.getLastName(),
                                     newEntity.getOrganization(),
                                     newEntity.getInternalAccessRights().getCrudOperations()
                                             .contains(CrudOperation.Create) == true ? 1 : 0,
                                     newEntity.getInternalAccessRights().getCrudOperations()
                                             .contains(CrudOperation.Read) == true ? 1 : 0,
                                     newEntity.getInternalAccessRights().getCrudOperations()
                                             .contains(CrudOperation.Update) == true ? 1 : 0,
                                     newEntity.getInternalAccessRights().getCrudOperations()
                                             .contains(CrudOperation.Delete) == true ? 1 : 0);
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
            client.callProcedure("EMPLOYEES.update",
                                    updatedEntity.getId(),
                                    updatedEntity.getFirstName(),
                                    updatedEntity.getLastName(),
                                    updatedEntity.getOrganization(),
                                    updatedEntity.getInternalAccessRights().getCrudOperations().contains(CrudOperation.Create) == true ? 1 : 0,
                                    updatedEntity.getInternalAccessRights().getCrudOperations().contains(CrudOperation.Read) == true ? 1 : 0,
                                    updatedEntity.getInternalAccessRights().getCrudOperations().contains(CrudOperation.Update) == true ? 1 : 0,
                                    updatedEntity.getInternalAccessRights().getCrudOperations().contains(CrudOperation.Delete) == true ? 1 : 0,
                                    id);
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
                Employee initializedEmployee = initializeEmployee(voltDBOrganizationList);
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

    private Employee initializeEmployee(VoltTable voltDBEmployee) {
        Employee employee = new Employee();

        employee.setId((String) voltDBEmployee.get("EmployeeId", VoltType.STRING));
        employee.setFirstName((String) voltDBEmployee.get("FirstName", VoltType.STRING));
        employee.setLastName((String) voltDBEmployee.get("LastName", VoltType.STRING));
        employee.setOrganization((String) voltDBEmployee.get("OrganizationId", VoltType.STRING));

        InternalAccessRights internalAccessRights = new InternalAccessRights();
        EnumSet<CrudOperation> crudOperations = EnumSet.noneOf(CrudOperation.class);
        if((Byte) voltDBEmployee.get("InternalCreate", VoltType.TINYINT) == 1 ? true : false){
            crudOperations.add(CrudOperation.Create);
        }
        if((Byte) voltDBEmployee.get("InternalRead", VoltType.TINYINT) == 1 ? true : false) {
            crudOperations.add(CrudOperation.Read);
        }
        if((Byte) voltDBEmployee.get("InternalUpdate", VoltType.TINYINT) == 1 ? true : false) {
            crudOperations.add(CrudOperation.Update);
        }
        if((Byte) voltDBEmployee.get("InternalDelete", VoltType.TINYINT) == 1 ? true : false){
            crudOperations.add(CrudOperation.Delete);
        }
        internalAccessRights.setCrudOperations(crudOperations);
        employee.setInternalAccessRights(internalAccessRights);

        return employee;
    }
}
