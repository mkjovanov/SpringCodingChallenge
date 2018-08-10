package access.rights.rest.api.product;

import access.rights.rest.api.access.rights.AccessRightsService;
import access.rights.rest.api.access.rights.entities.CrudOperation;
import access.rights.rest.api.product.entities.Product;
import access.rights.rest.api.product.repositories.ProductInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductInMemoryRepository productRepository;
    @Autowired
    private AccessRightsService accessRightsService;

    public List<Product> getAllProducts(String organizationId) {
        return accessRightsService.filterByAccessRights(organizationId);
    }

    public Product getProduct(String organizationId, String id) {
        if (accessRightsService.isCrudOperationAvailable(CrudOperation.Read, organizationId)) {
            return productRepository.get(id);
        }
        return null;
    }

    public void addProduct(String organizationId, Product newProduct) {
        if (accessRightsService.isCrudOperationAvailable(CrudOperation.Create, organizationId)) {
            productRepository.add(newProduct);
        }
    }

    public void updateProduct(String id, String organizationId, Product updatedProduct) {
        if (accessRightsService.isCrudOperationAvailable(CrudOperation.Update, organizationId)) {
            productRepository.update(id, updatedProduct);
        }
    }

    public void deleteProduct(String id, String organizationId) {
        if (accessRightsService.isCrudOperationAvailable(CrudOperation.Delete, organizationId)) {
            productRepository.delete(id);
        }
    }

    public List<Product> getAllProductsBypassAccessRights(String organizationId) {
        return productRepository.getAllByOrganizationId(organizationId);
    }
}
