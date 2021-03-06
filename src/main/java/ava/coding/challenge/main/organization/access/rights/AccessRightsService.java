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
                boolean isCrudOperationInterallyAvailable =
                        loggedInUser.getInternalAccessRights().getCrudOperations().contains(item.getCrudOperation());
                if(item.getQuantityRestriction() == null && isCrudOperationInterallyAvailable) {
                    crudOperations.add(item.getCrudOperation());
                } else if (isCrudOperationInterallyAvailable &&
                        (isLessThanConditionSatisfied(item, accessingProduct) ||
                           isGreaterThanConditionSatisfied(item, accessingProduct))) {
                            crudOperations.add(item.getCrudOperation());
                    }
                }
            return crudOperations;
        }
    }

    public void approveRequest(String id) {
        ApprovalRequest approvalRequest = approvalRequestService.getApprovalRequest(id);
        List<ExternalAccessRights> externalAccessRights = initializeExternalRights(approvalRequest);

        for (Iterator<ExternalAccessRights> i = externalAccessRights.iterator(); i.hasNext();) {
            ExternalAccessRights item = i.next();
            externalAccessRightsService.addExternalAccessRights(item);
        }

        approvalRequestService.deleteApprovalRequest(id);
    }

    public Employee getLoggedInUser() {
        UserDetails loggedInUserDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return employeeService.getEmployee(loggedInUserDetails.getUsername());
    }

    public boolean isCrudOperationAvailable(CrudOperation crudOperation, String organizationId) {
        return (isInternalAccessRight(organizationId) && isInternalOperationAvailable(crudOperation)) ||
                (isExternalOperationAvailable(crudOperation, organizationId));
    }

    public boolean isCrudOperationByProductAvailable(CrudOperation crudOperation, String organizationId, String productId) {
        return (isInternalAccessRight(organizationId) && isInternalOperationAvailable(crudOperation)) ||
                (isExternalOperationByProductAvailable(crudOperation, organizationId, productId));
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
            availableProducts.addAll(applyQuantityRestrictions(organizationId, CrudOperation.Read));
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

        return organization.getExternalAccessRightsList().stream()
                .filter(x -> x.getGivingOrganization().equals(organizationId) &&
                            x.getCrudOperation().equals(crudOperation)).count() != 0;
    }

    public boolean isExternalOperationByProductAvailable(CrudOperation crudOperation, String organizationId, String productId) {
        Employee loggedInUser = getLoggedInUser();
        Organization organization = organizationService.getOrganization(loggedInUser.getOrganization());
        if(!loggedInUser.getInternalAccessRights().getCrudOperations().contains(crudOperation) ||
            organization.getExternalAccessRightsList() == null ||
            organization.getExternalAccessRightsList().isEmpty()) {
            return false;
        }

        ExternalAccessRights externalAccessRights =
                        organization.getExternalAccessRightsList().stream()
                        .filter(x -> x.getGivingOrganization().equals(organizationId) &&
                        x.getCrudOperation().equals(crudOperation)).findFirst().get();

        return externalAccessRights.getQuantityRestriction() == null ||
                isQuantityRestrictionSatisfied(externalAccessRights, productId);
    }

    private boolean isLessThanConditionSatisfied(ExternalAccessRights externalAccessRights, Product accessingProduct) {
        return externalAccessRights.getQuantityRestriction().getRestrictingCondition().equals(RestrictingCondition.LessThan) &&
                accessingProduct.getStock() < externalAccessRights.getQuantityRestriction().getRestrictedAmount();
    }

    private boolean isGreaterThanConditionSatisfied(ExternalAccessRights externalAccessRights, Product accessingProduct) {
        return (externalAccessRights.getQuantityRestriction().getRestrictingCondition().equals(RestrictingCondition.GreaterThan) &&
                accessingProduct.getStock() > externalAccessRights.getQuantityRestriction().getRestrictedAmount());
    }

    private boolean isQuantityRestrictionSatisfied(ExternalAccessRights externalAccessRights, String productId) {
        QuantityRestriction quantityRestriction = externalAccessRights.getQuantityRestriction();
        Product product = productService.getProductBypassAccessRights(productId);
        boolean isLessRestriction = quantityRestriction.getRestrictingCondition().equals(RestrictingCondition.LessThan);

        return (isLessRestriction && product.getStock() < quantityRestriction.getRestrictedAmount()) ||
                product.getStock() > quantityRestriction.getRestrictedAmount();
    }

    private boolean isQuantityRestrictionAvailable(QuantityRestriction quantityRestriction) {
        return quantityRestriction != null;
    }

    private boolean isQuantityRestrictionLessThan(QuantityRestriction quantityRestriction) {
        return quantityRestriction.getRestrictingCondition().equals(RestrictingCondition.LessThan);
    }

    private boolean isQuantityRestrictionGreaterThan(QuantityRestriction quantityRestriction) {
        return quantityRestriction.getRestrictingCondition().equals(RestrictingCondition.GreaterThan);
    }

    private List<Product> filterByLessThanQuantity(String organizationId, Integer restrictedAmount) {
        return productService.getAllProductsBypassAccessRights(organizationId).stream()
                .filter(p -> p.getStock() < restrictedAmount)
                .collect(Collectors.toList());
    }

    private List<Product> filterByGreaterThanQuantity(String organizationId, Integer restrictedAmount) {
        return productService.getAllProductsBypassAccessRights(organizationId).stream()
                .filter(p -> p.getStock() > restrictedAmount)
                .collect(Collectors.toList());
    }

    private List<Product> applyQuantityRestrictions(String organizationId, CrudOperation crudOperation) {
        Employee loggedInUser = getLoggedInUser();
        ArrayList<Product> availableProducts = new ArrayList<>();
        QuantityRestriction quantityRestriction =
                organizationService.getOrganization(loggedInUser.getOrganization())
                        .getExternalAccessRightsList().stream()
                        .filter(x -> x.getGivingOrganization().equals(organizationId) &&
                                x.getCrudOperation().equals(crudOperation))
                        .findFirst().get().getQuantityRestriction();

        if (isQuantityRestrictionAvailable(quantityRestriction)) {
            if (isQuantityRestrictionLessThan(quantityRestriction)) {
                availableProducts
                        .addAll(filterByLessThanQuantity(organizationId, quantityRestriction.getRestrictedAmount()));
            }
            else if (isQuantityRestrictionGreaterThan(quantityRestriction)) {
                availableProducts
                        .addAll(filterByGreaterThanQuantity(organizationId, quantityRestriction.getRestrictedAmount()));
            }
        }
        else {
            availableProducts.addAll(productService.getAllProductsBypassAccessRights(organizationId));
        }

        return availableProducts;
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
}
