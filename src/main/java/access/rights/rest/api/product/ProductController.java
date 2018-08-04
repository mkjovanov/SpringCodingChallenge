package access.rights.rest.api.product;

import access.rights.rest.api.access.right.AccessRight;
import access.rights.rest.api.organization.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/organizations/{organizationId}/products")
    public List<Product> getAllProducts(@PathVariable("organizationId") String organizationId) {
        return productService.getAllProducts(organizationId);
    }

    @RequestMapping("/organizations/{organizationId}/products/{id}")
    public Product getProduct(@PathVariable("id") String id) {
        return productService.getProduct(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations/{organizationId}/products")
    public void addNewProduct(@PathVariable("organizationId") String organizationId, @RequestBody Product newProduct) {
        newProduct.setOrganization(new Organization(organizationId, ""));
        newProduct.setAccessRight(new AccessRight(true, true, true, true));
        productService.addProduct(newProduct);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/organizations/{organizationId}/products")
    public void addNewProductList(@PathVariable("organizationId") String organizationId, @RequestBody List<Product> productList) {
        productList.forEach(x -> {
            x.setOrganization(new Organization(organizationId, ""));
            x.setAccessRight(new AccessRight(true, true, true, true));
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
