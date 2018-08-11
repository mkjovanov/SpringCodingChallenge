package ava.coding.challenge.main.product;

import ava.coding.challenge.main.organization.access.rights.entities.access.rights.InternalAccessRights;
import ava.coding.challenge.main.product.entities.Product;
import ava.coding.challenge.main.product.entities.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/organizations/{organizationId}/products")
    @ResponseBody
    public ResponseEntity getAllProducts(@PathVariable("organizationId") String organizationId) {
        return productService.getAllProducts(organizationId);
    }

    @RequestMapping("/organizations/{organizationId}/products/{id}")
    @ResponseBody
    public ResponseEntity getProduct(@PathVariable("organizationId") String organizationId,
                                     @PathVariable("id") String id) {
        ProductResponse productResponse = new ProductResponse(
                productService.getProduct(organizationId, id).getBody(),
                new InternalAccessRights());
        return new ResponseEntity(productResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organizations/{organizationId}/products")
    public ResponseEntity addNewProduct(@PathVariable("organizationId") String organizationId,
                                        @RequestBody Product newProduct) {
        newProduct.setOrganization(organizationId);
        return productService.addProduct(organizationId, newProduct);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/organizations/{organizationId}/products/{id}")
    public ResponseEntity updateProduct(@PathVariable("organizationId") String organizationId,
                                        @RequestBody Product updatedProduct,
                                        @PathVariable String id) {
        if (updatedProduct.getOrganization() == null) {
            updatedProduct.setOrganization(organizationId);
        }
        return productService.updateProduct(id, organizationId, updatedProduct);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/organizations/{organizationId}/products/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") String id,
                                        @PathVariable("organizationId") String organizationId) {
        return productService.deleteProduct(id, organizationId);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/organizations/{organizationId}/products")
    public void addNewProductList(@PathVariable("organizationId") String organizationId,
                                  @RequestBody List<Product> productList) {
        productList.forEach(e -> e.setOrganization(organizationId));
        productList.forEach(e -> productService.addProduct(e.getOrganization(), e));
    }
}
