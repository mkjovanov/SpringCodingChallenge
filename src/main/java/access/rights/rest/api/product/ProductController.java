package access.rights.rest.api.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProductController {

    @RequestMapping("/product")
    public List<Product> getAllProducts() {
        return Arrays.asList(
                new Product(1, "Elektromotor", 550.00, 5),
                new Product(2, "Energetski kabel", 100.00, 8),
                new Product(3, "Osigurac", 20.00, 120)
        );
    }
}
