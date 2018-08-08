package access.rights.rest.api.access.rights;

import access.rights.rest.api.access.rights.entities.CrudOperation;
import access.rights.rest.api.access.rights.entities.QuantityRestriction;
import access.rights.rest.api.access.rights.entities.RestrictingCondition;
import access.rights.rest.api.access.rights.entities.access.rights.ExternalAccessRights;
import access.rights.rest.api.approval.request.ApprovalRequestService;
import access.rights.rest.api.approval.request.entities.ApprovalRequest;
import access.rights.rest.api.employee.entities.Employee;
import access.rights.rest.api.employee.repositories.EmployeeInMemoryRepository;
import access.rights.rest.api.organization.OrganizationService;
import access.rights.rest.api.organization.entities.Organization;
import access.rights.rest.api.product.entities.Product;
import access.rights.rest.api.product.repositories.ProductInMemoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessRightsService {

    @Autowired
    private ProductInMemoryRepository productRepository;
    @Autowired
    private EmployeeInMemoryRepository employeeRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ApprovalRequestService approvalRequestService;

    public List<Product> filterByInternalReadAllRights(String organizationId) {
        ArrayList<Product> availableProducts = new ArrayList<>();
        if (isInternalAccessRight(organizationId) && isInternalOperationAvailable(CrudOperation.Read)) {
            availableProducts.addAll(productRepository.getAllByOrganizationId(organizationId));
        }
        return availableProducts;
    }

/*    public List<Product> filterByExternalReadAllRights(String organizationId) {
        ArrayList<Product> availableProducts = new ArrayList<>();
        if (isExternalAccessRight(organizationId) && isExternalOperationAvailable(CrudOperation.Read)) {
            availableProducts.addAll(applyQuantityRestrictions(organizationId));
        }
        return availableProducts;
    }

    private List<Product> applyQuantityRestrictions(String organizationId) {
        ArrayList<Product> availableProducts = new ArrayList<>();
        QuantityRestriction quantityRestriction =
                organizationService.getMasterOrganization(organizationId).getExternalAccessRightsList().;

        if (isQuantityRestrictionAvailable(quantityRestriction)) {
            if (isQuantityLessThan(quantityRestriction)) {
                availableProducts
                        .addAll(filterByLessThanQuantity(organizationId, quantityRestriction.getRestrictedAmmount()));
            }
            else {
                availableProducts
                        .addAll(filterByGreaterThanQuantity(organizationId, quantityRestriction.getRestrictedAmmount()));
            }
        }
        else {
            availableProducts.addAll(productRepository.getAllByOrganizationId(organizationId));
        }

        return availableProducts;
    }*/

    public boolean isInternalAccessRight(String organizationId) {
        Employee loggedInUser = employeeRepository.get("pera.peric");
        return loggedInUser.getOrganization().getId().equals(organizationId);
    }

    public boolean isInternalOperationAvailable(CrudOperation crudOperation) {
        Employee loggedInUser = employeeRepository.get("pera.peric");
        return loggedInUser
                .getInternalExternalAccessRights()
                .getCrudOperations().stream()
                .anyMatch(c -> c == crudOperation);
    }


    public boolean isExternalOperationAvailable(CrudOperation crudOperation) {
        Employee loggedInUser = employeeRepository.get("pera.peric");
        throw new NotImplementedException();
/*        return loggedInUser
                .getInternalExternalAccessRights()
                .getCrudOperationSet().stream()
                .anyMatch(c -> c == crudOperation);*/
    }

/*    public boolean isExternalAccessRight(String organizationId) {
        Employee loggedInUser = employeeRepository.get("pera.peric");
        return loggedInUser.getExternalAccessRightsList().containsKey(organizationId);
    }*/

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
        return productRepository.getAllByOrganizationId(organizationId).stream()
                .filter(p -> p.getStock() < restrictedAmmount)
                .collect(Collectors.toList());
    }

    private List<Product> filterByGreaterThanQuantity(String organizationId, Integer restrictedAmmount) {
        return productRepository.getAllByOrganizationId(organizationId).stream()
                .filter(p -> p.getStock() > restrictedAmmount)
                .collect(Collectors.toList());
    }

    public void approveRequest(String id) {
        ApprovalRequest approvalRequest = approvalRequestService.getApprovalRequest(id);
        Organization requestingOrganization = organizationService.getOrganization(approvalRequest.getRequestingOrganization());
        requestingOrganization.getExternalAccessRightsList().add(approvalRequest.getExternalAccessRights());
        organizationService.updateOrganization(requestingOrganization.getId(), requestingOrganization);
    }
}
