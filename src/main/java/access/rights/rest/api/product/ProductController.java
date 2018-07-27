package access.rights.rest.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping("/products/{id}")
    public Product getProduct(@PathVariable Integer id) {
        return productService.getProduct(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/products")
    public void addNewProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/products/{id}")
    public void updateProduct(@RequestBody Product updatedProduct) {
        productService.updateProduct(updatedProduct);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/products/{id}")
    public void deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
    }
}
