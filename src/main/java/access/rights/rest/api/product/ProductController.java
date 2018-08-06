package access.rights.rest.api.product;

import access.rights.rest.api.access.rights.AccessRights;
import access.rights.rest.api.organization.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/organizations/{organizationId}/products")
    public List<Product> getAllProducts(@PathVariable("organizationId") String organizationId) {
        return productService.getAllProducts(organizationId);
    }

    @RequestMapping("/organizations/{organizationId}/products/{id}")
    public Product getProduct(@PathVariable("organizationId") String organizationId, @PathVariable("id") String id) throws AccessDeniedException {
        return productService.getProduct(organizationId, id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations/{organizationId}/products")
    public void addNewProduct(@PathVariable("organizationId") String organizationId, @RequestBody Product newProduct) {
        newProduct.setOrganization(new Organization(organizationId, ""));
        //TODO: Get logged in user access rights object
        newProduct.setAccessRights(new AccessRights());
        productService.addProduct(newProduct);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/organizations/{organizationId}/products")
    public void addNewProductList(@PathVariable("organizationId") String organizationId, @RequestBody List<Product> productList) {
        productList.forEach(x -> {
            x.setOrganization(new Organization(organizationId, ""));
            x.setAccessRights(new AccessRights(true, true, true, true, null));
        });
        productList.forEach(productService::addProduct);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/organizations/{organizationId}/products/{id}")
    public void updateProduct(@PathVariable("organizationId") String organizationId, @RequestBody Product updatedProduct, @PathVariable String id) {
        //TODO: is this needed?
        updatedProduct.setOrganization(new Organization(organizationId, ""));
        productService.updateProduct(id, updatedProduct);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/organizations/{organizationId}/products/{id}")
    public void deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
    }
}
