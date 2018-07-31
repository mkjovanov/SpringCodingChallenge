package access.rights.rest.api.product;

import access.rights.rest.api.organization.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/organizations/{organizationId}/products")
    public List<Product> getAllProducts(@PathVariable("organizationId") Integer organizationId) {
        return productService.getAllProducts(organizationId);
    }

    @RequestMapping("/organizations/{organizationId}/products/{id}")
    public Product getProduct(@PathVariable("id") Integer id) {
        return productService.getProduct(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations/{organizationId}/products")
    public void addNewProduct(@PathVariable("organizationId") Integer organizationId, @RequestBody Product newProduct) {
        newProduct.setOrganization(new Organization(organizationId, ""));
        productService.addProduct(newProduct);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/organizations/{organizationId}/products")
    public void addNewProductList(@PathVariable("organizationId") Integer organizationId, @RequestBody List<Product> productList) {
        productList.forEach(x -> x.setOrganization(new Organization(organizationId, "")));
        productList.forEach(productService::addProduct);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/organizations/{organizationId}/products/{id}")
    public void updateProduct(@PathVariable("organizationId") Integer organizationId, @RequestBody Product updatedProduct) {
        updatedProduct.setOrganization(new Organization(organizationId, ""));
        productService.updateProduct(updatedProduct);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/organizations/{organizationId}/products/{id}")
    public void deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
    }
}
