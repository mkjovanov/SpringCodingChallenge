package access.rights.rest.api.access.rights;

import access.rights.rest.api.access.rights.entities.CrudOperation;
import access.rights.rest.api.access.rights.entities.QuantityRestriction;
import access.rights.rest.api.access.rights.entities.RestrictingCondition;
import access.rights.rest.api.access.rights.entities.access.rights.ExternalAccessRights;
import access.rights.rest.api.approval.request.ApprovalRequestService;
import access.rights.rest.api.approval.request.entities.ApprovalRequest;
import access.rights.rest.api.employee.EmployeeService;
import access.rights.rest.api.employee.entities.Employee;
import access.rights.rest.api.organization.OrganizationService;
import access.rights.rest.api.organization.entities.Organization;
import access.rights.rest.api.product.ProductService;
import access.rights.rest.api.product.entities.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessRightsService {

    @Autowired
    private ProductService productService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ApprovalRequestService approvalRequestService;

    public void approveRequest(String id) {
        ApprovalRequest approvalRequest = approvalRequestService.getApprovalRequest(id);
        Organization requestingOrganization = organizationService.getOrganization(approvalRequest.getRequestingOrganization());
        requestingOrganization.getExternalAccessRightsList().add(approvalRequest.getExternalAccessRights());
        organizationService.updateOrganization(requestingOrganization.getId(), requestingOrganization);
        approvalRequestService.deleteApprovalRequest(id);
    }

    public boolean isCrudOperationAvailable(CrudOperation crudOperation, String organizationId) {
        return (isInternalAccessRight(organizationId) && isInternalOperationAvailable(crudOperation)) ||
                (isExternalOperationAvailable(crudOperation, organizationId));
    }

    public List<Product> filterByAccessRights(String organizationId) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        if (isInternalAccessRight(organizationId) &&
                isInternalOperationAvailable(CrudOperation.Read)) {
            filteredProducts.addAll(filterByInternalReadAllRights(organizationId));
        }
        else if (isExternalOperationAvailable(CrudOperation.Read, organizationId)) {
            filteredProducts.addAll(filterByExternalReadAllRights(organizationId));
        }
        return filteredProducts;
    }

    public List<Product> filterByInternalReadAllRights(String organizationId) {
        ArrayList<Product> availableProducts = new ArrayList<>();
        if (isInternalAccessRight(organizationId) && isInternalOperationAvailable(CrudOperation.Read)) {
            availableProducts.addAll(productService.getAllProductsBypassAccessRights(organizationId));
        }
        return availableProducts;
    }

    public List<Product> filterByExternalReadAllRights(String organizationId) {
        ArrayList<Product> availableProducts = new ArrayList<>();
        if (!isInternalAccessRight(organizationId) &&
            isInternalOperationAvailable(CrudOperation.Read) &&
            isExternalOperationAvailable(CrudOperation.Read, organizationId)) {
            availableProducts.addAll(applyQuantityRestrictions(organizationId));
        }
        return availableProducts;
    }

    public boolean isInternalAccessRight(String organizationId) {
        Employee loggedInUser = employeeService.getEmployee("pera.peric");
        return loggedInUser.getOrganization().getId().equals(organizationId);
    }

    public boolean isInternalOperationAvailable(CrudOperation crudOperation) {
        Employee loggedInUser = employeeService.getEmployee("pera.peric");
        return loggedInUser
                .getInternalAccessRights()
                .getCrudOperations().stream()
                .anyMatch(c -> c.equals(crudOperation));
    }

    public boolean isExternalOperationAvailable(CrudOperation crudOperation, String organizationId) {
        Employee loggedInUser = employeeService.getEmployee("pera.peric");
        Organization organization = organizationService.getOrganization(loggedInUser.getOrganization().getId());
        boolean isExternalRightsMatch = organization.getExternalAccessRightsList().stream()
                .anyMatch(x -> x.getSharedOrganization().equals(organizationId));
        return isExternalRightsMatch &&
                organization.getExternalAccessRightsList().stream()
                .filter(x -> x.getSharedOrganization().equals(organizationId))
                .findFirst().get()
                .getCrudOperations().stream()
                .anyMatch(y -> y.equals(crudOperation));
    }

    private List<Product> applyQuantityRestrictions(String organizationId) {
        Employee loggedInUser = employeeService.getEmployee("pera.peric");
        ArrayList<Product> availableProducts = new ArrayList<>();
        QuantityRestriction quantityRestriction =
                organizationService.getOrganization(loggedInUser.getOrganization().getId())
                        .getExternalAccessRightsList().stream()
                        .filter(x -> x.getSharedOrganization().equals(organizationId))
                        .findFirst().get().getQuantityRestriction();

        if (isQuantityRestrictionAvailable(quantityRestriction)) {
            if (isQuantityLessThan(quantityRestriction)) {
                availableProducts
                        .addAll(filterByLessThanQuantity(organizationId, quantityRestriction.getRestrictedAmmount()));
            }
            else if (isQuantityGreaterThan(quantityRestriction)) {
                availableProducts
                        .addAll(filterByGreaterThanQuantity(organizationId, quantityRestriction.getRestrictedAmmount()));
            }
        }
        else {
            availableProducts.addAll(productService.getAllProductsBypassAccessRights(organizationId));
        }

        return availableProducts;
    }

    private boolean isQuantityRestrictionAvailable(QuantityRestriction quantityRestriction) {
        return quantityRestriction != null;
    }

    private boolean isQuantityLessThan(QuantityRestriction quantityRestriction) {
        return quantityRestriction.getRestrictingCondition().equals(RestrictingCondition.LessThan);
    }

    private boolean isQuantityGreaterThan(QuantityRestriction quantityRestriction) {
        return quantityRestriction.getRestrictingCondition().equals(RestrictingCondition.GreaterThan);
    }

    private List<Product> filterByLessThanQuantity(String organizationId, Integer restrictedAmmount) {
        return productService.getAllProductsBypassAccessRights(organizationId).stream()
                .filter(p -> p.getStock() < restrictedAmmount)
                .collect(Collectors.toList());
    }

    private List<Product> filterByGreaterThanQuantity(String organizationId, Integer restrictedAmmount) {
        return productService.getAllProductsBypassAccessRights(organizationId).stream()
                .filter(p -> p.getStock() > restrictedAmmount)
                .collect(Collectors.toList());
    }
}
