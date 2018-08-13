package ava.coding.challenge.main.organization.access.rights;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.access.rights.entities.QuantityRestriction;
import ava.coding.challenge.main.organization.access.rights.entities.RestrictingCondition;
import ava.coding.challenge.main.organization.approval.request.ApprovalRequestService;
import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
import ava.coding.challenge.main.employee.EmployeeService;
import ava.coding.challenge.main.employee.entities.Employee;
import ava.coding.challenge.main.organization.OrganizationService;
import ava.coding.challenge.main.organization.entities.Organization;
import ava.coding.challenge.main.product.ProductService;
import ava.coding.challenge.main.product.entities.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
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

    public EnumSet<CrudOperation> getCurrentUserAccessRights(String organizationId) {
        Employee loggedInUser = employeeService.getEmployee("pera.peric");
        if (loggedInUser.getOrganization().equals(organizationId)) {
            return loggedInUser.getInternalAccessRights().getCrudOperations();
        }
        else {
            Organization loggedInUsesOrganization = organizationService.getOrganization(loggedInUser.getOrganization());
            Organization  accessingOrganization = organizationService.getOrganization(organizationId);
            return accessingOrganization.getExternalAccessRightsList().stream()
                    .filter(e -> e.getSharedOrganization().equals(loggedInUsesOrganization.getId()))
                    .findFirst()
                    .get()
                    .getCrudOperations();
        }
    }

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
        return loggedInUser.getOrganization().equals(organizationId);
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
        Organization organization = organizationService.getOrganization(loggedInUser.getOrganization());
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
                organizationService.getOrganization(loggedInUser.getOrganization())
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
