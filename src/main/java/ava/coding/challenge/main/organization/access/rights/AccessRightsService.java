package ava.coding.challenge.main.organization.access.rights;

import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.access.rights.entities.QuantityRestriction;
import ava.coding.challenge.main.organization.access.rights.entities.RestrictingCondition;
import ava.coding.challenge.main.organization.access.rights.entities.access.rights.ExternalAccessRights;
import ava.coding.challenge.main.organization.approval.request.ApprovalRequestService;
import ava.coding.challenge.main.organization.approval.request.entities.ApprovalRequest;
import ava.coding.challenge.main.employee.EmployeeService;
import ava.coding.challenge.main.employee.entities.Employee;
import ava.coding.challenge.main.organization.OrganizationService;
import ava.coding.challenge.main.organization.entities.Organization;
import ava.coding.challenge.main.product.ProductService;
import ava.coding.challenge.main.product.entities.Product;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
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
    @Autowired
    private ExternalAccessRightsService externalAccessRightsService;

    public EnumSet<CrudOperation> getCurrentUserAccessRights(String organizationId, Product accessingProduct) {
        Employee loggedInUser = getLoggedInUser();
        if (loggedInUser.getOrganization().equals(organizationId)) {
            return loggedInUser.getInternalAccessRights().getCrudOperations();
        }
        else {
            Organization loggedInUserOrganization = organizationService.getOrganization(loggedInUser.getOrganization());
            EnumSet<CrudOperation> crudOperations = EnumSet.noneOf(CrudOperation.class);

            List<ExternalAccessRights> crudOperationsList = loggedInUserOrganization.getExternalAccessRightsList();
            for (Iterator<ExternalAccessRights> i = crudOperationsList.iterator(); i.hasNext();) {
                ExternalAccessRights item = i.next();
                if(item.getQuantityRestriction() != null) {
                    if ((item.getQuantityRestriction().getRestrictingCondition().equals(RestrictingCondition.LessThan) &&
                        accessingProduct.getStock() < item.getQuantityRestriction().getRestrictedAmount()) ||
                        (item.getQuantityRestriction().getRestrictingCondition().equals(RestrictingCondition.GreaterThan) &&
                                accessingProduct.getStock() > item.getQuantityRestriction().getRestrictedAmount())) {
                        crudOperations.add(item.getCrudOperation());
                    }
                }
            }

            return crudOperations;
        }
    }

    public void approveRequest(String id) {
        ApprovalRequest approvalRequest = approvalRequestService.getApprovalRequest(id);
        EnumSet<CrudOperation> crudOperations = approvalRequest.getRequestingRights().getCrudOperations();
        List<ExternalAccessRights> externalAccessRights = initializeExternalRights(approvalRequest);

        for (Iterator<ExternalAccessRights> i = externalAccessRights.iterator(); i.hasNext();) {
            ExternalAccessRights item = i.next();
            externalAccessRightsService.addExternalAccessRights(item);
        }
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
        return getLoggedInUser().getOrganization().equals(organizationId);
    }

    public boolean isInternalOperationAvailable(CrudOperation crudOperation) {
        return getLoggedInUser()
                .getInternalAccessRights()
                .getCrudOperations().stream()
                .anyMatch(c -> c.equals(crudOperation));
    }

    public boolean isExternalOperationAvailable(CrudOperation crudOperation, String organizationId) {
        Employee loggedInUser = getLoggedInUser();
        Organization organization = organizationService.getOrganization(loggedInUser.getOrganization());
        if(organization.getExternalAccessRightsList() == null ||
           organization.getExternalAccessRightsList().isEmpty()) {
            return false;
        }

        boolean isExternalRightsMatch = organization.getExternalAccessRightsList().stream()
                .anyMatch(x -> x.getGivingOrganization().equals(organizationId));
        return isExternalRightsMatch &&
                organization.getExternalAccessRightsList().stream()
                .filter(x -> x.getGivingOrganization().equals(organizationId) &&
                             x.getCrudOperation().equals(crudOperation)).count() != 0;
    }

    private List<Product> applyQuantityRestrictions(String organizationId) {
        Employee loggedInUser = getLoggedInUser();
        ArrayList<Product> availableProducts = new ArrayList<>();
        QuantityRestriction quantityRestriction =
                organizationService.getOrganization(loggedInUser.getOrganization())
                        .getExternalAccessRightsList().stream()
                        .filter(x -> x.getGivingOrganization().equals(organizationId))
                        .findFirst().get().getQuantityRestriction();

        if (isQuantityRestrictionAvailable(quantityRestriction)) {
            if (isQuantityLessThan(quantityRestriction)) {
                availableProducts
                        .addAll(filterByLessThanQuantity(organizationId, quantityRestriction.getRestrictedAmount()));
            }
            else if (isQuantityGreaterThan(quantityRestriction)) {
                availableProducts
                        .addAll(filterByGreaterThanQuantity(organizationId, quantityRestriction.getRestrictedAmount()));
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

    private List<ExternalAccessRights> initializeExternalRights(ApprovalRequest approvalRequest) {

        List<ExternalAccessRights> externalAccessRightsList = new ArrayList<>();
        EnumSet<CrudOperation> crudOperationsEnum = approvalRequest.getRequestingRights().getCrudOperations();
        List<CrudOperation> crudOperationsList = new ArrayList<>();

        for(CrudOperation crudOperation : crudOperationsEnum) {
            crudOperationsList.add(crudOperation);
        }

        for (Iterator<CrudOperation> i = crudOperationsList.iterator(); i.hasNext();) {
            ExternalAccessRights externalAccessRight = new ExternalAccessRights();
            externalAccessRight.setReceivingOrganization(approvalRequest.getRequestingOrganization());
            externalAccessRight.setGivingOrganization(approvalRequest.getRequestingRights().getSharingOrganization());
            externalAccessRight.setQuantityRestriction(approvalRequest.getRequestingRights().getQuantityRestriction());
            CrudOperation item = i.next();
            externalAccessRight.setCrudOperation(item);
            externalAccessRightsList.add(externalAccessRight);
        }

        return externalAccessRightsList;
    }

    public Employee getLoggedInUser() {
        UserDetails loggedInUserDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return employeeService.getEmployee(loggedInUserDetails.getUsername());
    }
}
