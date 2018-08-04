package access.rights.rest.api.product;

import access.rights.rest.api.product.repository.ProductInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductInMemoryRepository productRepository;

    public List<Product> getAllProducts(Integer organizationId) {
        return productRepository.getAllByOrganizationId(organizationId);
    }

    public Product getProduct(Integer id) {
        return productRepository.get(id);
    }

    public void addProduct(Product newProduct) {
        productRepository.add(newProduct);
    }

    public void updateProduct(Product updatedProduct) {
        productRepository.update(updatedProduct);
    }

    public void deleteProduct(Integer id) {
        productRepository.delete(id);
    }
}
