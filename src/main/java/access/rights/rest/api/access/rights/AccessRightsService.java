package access.rights.rest.api.access.rights;

import access.rights.rest.api.employee.Employee;
import access.rights.rest.api.employee.repository.EmployeeInMemoryRepository;
import access.rights.rest.api.product.Product;
import access.rights.rest.api.product.repository.ProductInMemoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessRightsService {

    @Autowired
    private ProductInMemoryRepository productRepository;
    @Autowired
    private EmployeeInMemoryRepository employeeRepository;
    //TODO: Change to read from the actual logged in user
    //private Employee loggedInUser = employeeRepository.get("pera.peric");

    public List<Product> filterByInternalReadAllRights(String organizationId) {
        ArrayList<Product> availableProducts = new ArrayList<>();
        if (isInternalAccessRight(organizationId) && isInternalReadAvailable()) {
            availableProducts.addAll(productRepository.getAllByOrganizationId(organizationId));
        }
        return availableProducts;
    }

    public List<Product> filterByExternalReadAllRights(String organizationId) {
        ArrayList<Product> availableProducts = new ArrayList<>();
        if (isExternalAccessRight(organizationId) && isExternalReadAvailable(organizationId)) {
            availableProducts.addAll(applyQuantityRestrictions(organizationId));
        }
        return availableProducts;
    }

    private List<Product> applyQuantityRestrictions(String organizationId) {
        Employee loggedInUser = employeeRepository.get("pera.peric");
        ArrayList<Product> availableProducts = new ArrayList<>();
        QuantityRestriction quantityRestriction =
                loggedInUser.getExternalAccessRightsList().get(organizationId).getQuantityRestriction();

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
    }

    public boolean isInternalAccessRight(String organizationId) {
        Employee loggedInUser = employeeRepository.get("pera.peric");
        return loggedInUser.getOrganization().getId().equals(organizationId);
    }

    public boolean isInternalReadAvailable() {
        Employee loggedInUser = employeeRepository.get("pera.peric");
        return loggedInUser.getInternalAccessRights().isRead();
    }

    public boolean isExternalAccessRight(String organizationId) {
        Employee loggedInUser = employeeRepository.get("pera.peric");
        return loggedInUser.getExternalAccessRightsList().containsKey(organizationId);
    }

    public boolean isExternalReadAvailable(String organizationId) {
        Employee loggedInUser = employeeRepository.get("pera.peric");
        return loggedInUser.getExternalAccessRightsList().get(organizationId).isRead();
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
        return productRepository.getAllByOrganizationId(organizationId).stream()
                .filter(p -> p.getStock() < restrictedAmmount)
                .collect(Collectors.toList());
    }

    private List<Product> filterByGreaterThanQuantity(String organizationId, Integer restrictedAmmount) {
        return productRepository.getAllByOrganizationId(organizationId).stream()
                .filter(p -> p.getStock() > restrictedAmmount)
                .collect(Collectors.toList());
    }
}
