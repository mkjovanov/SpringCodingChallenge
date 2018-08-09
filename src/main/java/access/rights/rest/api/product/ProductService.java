package access.rights.rest.api.product;

import access.rights.rest.api.access.rights.AccessRightsService;
import access.rights.rest.api.access.rights.entities.CrudOperation;
import access.rights.rest.api.product.entities.Product;
import access.rights.rest.api.product.repositories.ProductInMemoryRepository;
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
            accessRightsService.isInternalOperationAvailable(CrudOperation.Read)) {
            availableProducts.addAll(accessRightsService.filterByInternalReadAllRights(organizationId));
        }
        else if (accessRightsService.isExternalOperationAvailable(CrudOperation.Read, organizationId)) {
            availableProducts.addAll(accessRightsService.filterByExternalReadAllRights(organizationId));
        }
        return availableProducts;
    }

    public Product getProduct(String organizationId, String id)  throws AccessDeniedException {
        if (accessRightsService.isInternalAccessRight(organizationId) &&
            accessRightsService.isInternalOperationAvailable(CrudOperation.Read)) {
            return productRepository.get(id);
        }
        throw new AccessDeniedException("Access denied");
    }

    public void addProduct(Product newProduct) throws AccessDeniedException {
        if (accessRightsService.isInternalOperationAvailable(CrudOperation.Create)) {
            productRepository.add(newProduct);
        }
        else {
            throw new AccessDeniedException("Access denied");
        }
    }

    public void updateProduct(String id, Product updatedProduct) {
        productRepository.update(id, updatedProduct);
    }

    public void deleteProduct(String id) {
        productRepository.delete(id);
    }

    public List<Product> getAllProductsBypassAccessRights(String organizationId) {
        return productRepository.getAllByOrganizationId(organizationId);
    }
}
