package access.rights.rest.api.product;

import access.rights.rest.api.access.rights.AccessRightsService;
import access.rights.rest.api.access.rights.entities.CrudOperation;
import access.rights.rest.api.product.entities.Product;
import access.rights.rest.api.product.repositories.ProductInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductInMemoryRepository productRepository;
    @Autowired
    private AccessRightsService accessRightsService;

    public ResponseEntity<List<Product>> getAllProducts(String organizationId) {
        List<Product> availableProducts = accessRightsService.filterByAccessRights(organizationId);
        if (availableProducts.isEmpty()) {
            return new ResponseEntity("Reading products from organization: '" + organizationId + "' is forbidden for this user", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(availableProducts, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Product> getProduct(String organizationId, String id) {
        if (!accessRightsService.isCrudOperationAvailable(CrudOperation.Read, organizationId)) {
            return new ResponseEntity("Reading product from organization: '" + organizationId + "' is forbidden for this user", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(productRepository.get(id), HttpStatus.ACCEPTED);
    }

    public ResponseEntity addProduct(String organizationId, Product newProduct) {
        if (!accessRightsService.isCrudOperationAvailable(CrudOperation.Create, organizationId)) {
            return new ResponseEntity("Adding new product from organization: '" + organizationId + "' is forbidden for this user", HttpStatus.FORBIDDEN);
        }
        productRepository.add(newProduct);
        return new ResponseEntity(newProduct, HttpStatus.CREATED);
    }

    public ResponseEntity updateProduct(String id, String organizationId, Product updatedProduct) {
        if (!accessRightsService.isCrudOperationAvailable(CrudOperation.Update, organizationId)) {
            return new ResponseEntity("Updating product from organization: '" + organizationId + "' is forbidden for this user", HttpStatus.FORBIDDEN);
        }
        productRepository.update(id, updatedProduct);
        return new ResponseEntity(updatedProduct, HttpStatus.ACCEPTED);
    }

    public ResponseEntity deleteProduct(String id, String organizationId) {
        if (!accessRightsService.isCrudOperationAvailable(CrudOperation.Delete, organizationId)) {
            return new ResponseEntity("Deleting product from organization: '" + organizationId + "' is forbidden for this user", HttpStatus.FORBIDDEN);
        }
        productRepository.delete(id);
        return new ResponseEntity(id, HttpStatus.ACCEPTED);
    }

    public List<Product> getAllProductsBypassAccessRights(String organizationId) {
        return productRepository.getAllByOrganizationId(organizationId);
    }
}
