package access.rights.rest.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public Product getProduct(Integer id) {
        return productRepository.findById(id).get();
    }

    public void addProduct(Product newProduct) {
        productRepository.save(newProduct);
    }

    public void updateProduct(Product updatedProduct) {
        productRepository.save(updatedProduct);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
