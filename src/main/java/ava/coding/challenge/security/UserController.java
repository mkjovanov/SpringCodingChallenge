package ava.coding.challenge.security;

import ava.coding.challenge.main.employee.entities.Employee;
import ava.coding.challenge.main.organization.OrganizationService;
import ava.coding.challenge.main.organization.access.rights.AccessRightsService;
import ava.coding.challenge.main.organization.access.rights.entities.access.rights.ExternalAccessRights;
import ava.coding.challenge.main.organization.entities.Organization;
import ava.coding.challenge.main.product.ProductService;
import ava.coding.challenge.main.product.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private AccessRightsService accessRightsService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showLoginPage() {
        return "Welcome!";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> showAvailableProducts() {
        Employee employee = accessRightsService.getLoggedInUser();
        Organization organization = organizationService.getOrganization(employee.getOrganization());
        List<Product> availableProducts = productService.getAllProducts(organization.getId()).getBody();

        for (Iterator<ExternalAccessRights> i = organization.getExternalAccessRightsList().iterator(); i.hasNext();) {
            ExternalAccessRights item = i.next();
            availableProducts.addAll(productService.getAllProducts(item.getGivingOrganization()).getBody());
        }
        return availableProducts;
    }
}
