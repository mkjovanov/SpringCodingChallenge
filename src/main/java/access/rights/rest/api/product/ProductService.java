package access.rights.rest.api.product;

import access.rights.rest.api.access.rights.AccessRightsService;
import access.rights.rest.api.product.repository.ProductInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductInMemoryRepository productRepository;
    @Autowired
    private AccessRightsService accessRightsService;

    public List<Product> getAllProducts(String organizationId) {
        ArrayList<Product> availableProducts = new ArrayList<>();

        if (accessRightsService.isInternalAccessRight(organizationId) &&
            accessRightsService.isInternalReadAvailable()) {
            availableProducts.addAll(accessRightsService.filterByInternalReadAllRights(organizationId));
        }
        else if (accessRightsService.isExternalAccessRight(organizationId) &&
                 accessRightsService.isExternalReadAvailable(organizationId)) {
            availableProducts.addAll(accessRightsService.filterByExternalReadAllRights(organizationId));
        }

        return availableProducts;
    }

    public Product getProduct(String organizationId, String id)  throws AccessDeniedException {
        if(!accessRightsService.isInternalReadAvailable() ||
           !accessRightsService.isExternalReadAvailable(organizationId)) {
            throw new AccessDeniedException("", "", "");
        }
        if (!(accessRightsService.isInternalAccessRight(organizationId) &&
            accessRightsService.isInternalReadAvailable()) ||
            !(accessRightsService.isExternalAccessRight(organizationId) &&
            accessRightsService.isExternalReadAvailable(organizationId))) {
            throw new AccessDeniedException("", "", "");
        }
        return productRepository.get(id);
    }

    public void addProduct(Product newProduct) {
        productRepository.add(newProduct);
    }

    public void updateProduct(String id, Product updatedProduct) {
        productRepository.update(id, updatedProduct);
    }

    public void deleteProduct(String id) {
        productRepository.delete(id);
    }
}
