package ava.coding.challenge.helpers;

import ava.coding.challenge.main.employee.EmployeeService;
import ava.coding.challenge.main.employee.entities.Employee;
import ava.coding.challenge.main.organization.MasterOrganizationService;
import ava.coding.challenge.main.organization.OrganizationService;
import ava.coding.challenge.main.organization.approval.request.ApprovalRequestService;
import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
import ava.coding.challenge.main.organization.entities.MasterOrganization;
import ava.coding.challenge.main.organization.entities.Organization;
import ava.coding.challenge.main.product.ProductService;
import ava.coding.challenge.main.product.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NullHelper {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MasterOrganizationService masterOrganizationService;
    @Autowired
    private ApprovalRequestService approvalRequestService;

    public Employee sanitizeNullValues(String id, Employee updatedEmployee) {
        Employee sanitizedEmployee = updatedEmployee;
        Employee employeeFromDb = employeeService.getEmployee(id);

        if(updatedEmployee.getId() == null) {
            sanitizedEmployee.setId(employeeFromDb.getId());
        }
        if(updatedEmployee.getFirstName() == null) {
            sanitizedEmployee.setFirstName(employeeFromDb.getFirstName());
        }
        if(updatedEmployee.getLastName() == null) {
            sanitizedEmployee.setLastName(employeeFromDb.getLastName());
        }
        if(updatedEmployee.getOrganization() == null) {
            sanitizedEmployee.setOrganization(employeeFromDb.getOrganization());
        }
        if(updatedEmployee.getInternalAccessRights() == null) {
            sanitizedEmployee.setInternalAccessRights(employeeFromDb.getInternalAccessRights());
        }

        return sanitizedEmployee;
    }

    public Organization sanitizeNullValues(String id, Organization updatedOrganization) {
        Organization sanitizedOrganization = updatedOrganization;
        Organization organizationFromDb = organizationService.getOrganization(id);

        if(updatedOrganization.getId() == null) {
            sanitizedOrganization.setId(organizationFromDb.getId());
        }
        if(updatedOrganization.getName() == null) {
            sanitizedOrganization.setName(organizationFromDb.getName());
        }
        if(updatedOrganization.getExternalAccessRightsList() == null) {
            sanitizedOrganization.setExternalAccessRightsList(organizationFromDb.getExternalAccessRightsList());
        }

        return sanitizedOrganization;
    }

    public Product sanitizeNullValues(String id, Product updatedProduct) {
        Product sanitizedProduct = updatedProduct;
        Product productFromDb = productService.getProductBypassAccessRights(id);

        if(updatedProduct.getId() == null) {
            sanitizedProduct.setId(productFromDb.getId());
        }
        if(updatedProduct.getName() == null) {
            sanitizedProduct.setName(productFromDb.getName());
        }
        if(updatedProduct.getOrganization() == null) {
            sanitizedProduct.setOrganization(productFromDb.getOrganization());
        }
        if(updatedProduct.getStock() == null) {
            sanitizedProduct.setStock(productFromDb.getStock());
        }
        if(updatedProduct.getPrice() == null) {
            sanitizedProduct.setPrice(productFromDb.getPrice());
        }

        return sanitizedProduct;
    }

    public MasterOrganization sanitizeNullValues(String id, MasterOrganization updatedMasterOrganization) {
        MasterOrganization sanitizedMasterOrganization = updatedMasterOrganization;
        MasterOrganization updatedMasterOrganizationFromDb = masterOrganizationService.getMasterOrganization(id);

        if(updatedMasterOrganization.getId() == null) {
            sanitizedMasterOrganization.setId(updatedMasterOrganizationFromDb.getId());
        }
        if(updatedMasterOrganization.getName() == null) {
            sanitizedMasterOrganization.setName(updatedMasterOrganizationFromDb.getName());
        }

        return sanitizedMasterOrganization;
    }

    public ApprovalRequest sanitizeNullValues(String id, ApprovalRequest updatedApprovalRequest) {
        ApprovalRequest sanitizedApprovalRequest = updatedApprovalRequest;
        ApprovalRequest updatedApprovalRequestFromDb = approvalRequestService.getApprovalRequest(id);

        if(updatedApprovalRequest.getId() == null) {
            sanitizedApprovalRequest.setId(updatedApprovalRequestFromDb.getId());
        }
        if(updatedApprovalRequest.getExternalAccessRights() == null) {
            sanitizedApprovalRequest.setExternalAccessRights(updatedApprovalRequestFromDb.getExternalAccessRights());
        }
        if(updatedApprovalRequest.getRequestingOrganization() == null) {
            sanitizedApprovalRequest.setRequestingOrganization(updatedApprovalRequestFromDb.getRequestingOrganization());
        }

        return sanitizedApprovalRequest;
    }
}
