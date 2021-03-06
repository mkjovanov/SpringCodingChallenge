package ava.coding.challenge.main.product;

import ava.coding.challenge.helpers.NullHelper;
import ava.coding.challenge.main.organization.access.rights.AccessRightsService;
import ava.coding.challenge.main.organization.access.rights.entities.CrudOperation;
import ava.coding.challenge.main.organization.access.rights.entities.access.rights.InternalAccessRights;
import ava.coding.challenge.main.organization.entities.Organization;
import ava.coding.challenge.main.product.entities.Product;
import ava.coding.challenge.main.product.entities.ProductResponse;
import ava.coding.challenge.main.product.repositories.ProductInMemoryRepository;
import ava.coding.challenge.main.product.repositories.ProductVoltDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductVoltDBRepository productRepository;

    @Autowired
    private AccessRightsService accessRightsService;
    @Autowired
    private NullHelper nullHelper;

    public ResponseEntity<List<Product>> getAllProducts(String organizationId) {
        List<Product> availableProducts = accessRightsService.filterByAccessRights(organizationId);
        if (availableProducts.isEmpty()) {
            return new ResponseEntity("Reading products from organization: '" + organizationId +
                    "' is forbidden for this user or the product list is empty.",
                    HttpStatus.FORBIDDEN);
        }

        List<ProductResponse> productResponses = new ArrayList<>();
        for (Iterator<Product> i = availableProducts.iterator(); i.hasNext();) {
            Product item = i.next();
            EnumSet<CrudOperation> crudOperations = accessRightsService.getCurrentUserAccessRights(organizationId, item);
            productResponses.add(new ProductResponse(item, crudOperations));
        }

        return new ResponseEntity(productResponses, HttpStatus.OK);
    }

    public ResponseEntity<Product> getProduct(String organizationId, String id) {
        if (!accessRightsService.isCrudOperationByProductAvailable(CrudOperation.Read, organizationId, id)) {
            return new ResponseEntity("Reading product from organization: '" + organizationId +
                    "' is forbidden for this user.",
                    HttpStatus.FORBIDDEN);
        }
        Product product =  productRepository.get(id);
        ProductResponse productResponse = new ProductResponse(
                product,
                accessRightsService.getCurrentUserAccessRights(organizationId, product));
        return new ResponseEntity(productResponse, HttpStatus.OK);
    }

    public ResponseEntity addProduct(String organizationId, Product newProduct) {
        if (!accessRightsService.isCrudOperationAvailable(CrudOperation.Create, organizationId)) {
            return new ResponseEntity("Adding new product from organization: '" + organizationId +
                    "' is forbidden for this user",
                    HttpStatus.FORBIDDEN);
        }
        productRepository.add(newProduct);
        return new ResponseEntity(newProduct, HttpStatus.CREATED);
    }

    public ResponseEntity updateProduct(String id, String organizationId, Product updatedProduct) {
        if (!accessRightsService.isCrudOperationByProductAvailable(CrudOperation.Update, organizationId, id)) {
            return new ResponseEntity("Updating product from organization: '" + organizationId + "' is forbidden for this user", HttpStatus.FORBIDDEN);
        }

        Product sanitizedProduct = nullHelper.sanitizeNullValues(id, updatedProduct);
        productRepository.update(id, sanitizedProduct);

        return new ResponseEntity(updatedProduct, HttpStatus.ACCEPTED);
    }

    public ResponseEntity deleteProduct(String id, String organizationId) {
        if (!accessRightsService.isCrudOperationByProductAvailable(CrudOperation.Delete, organizationId, id)) {
            return new ResponseEntity("Deleting product from organization: '" + organizationId + "' is forbidden for this user", HttpStatus.FORBIDDEN);
        }
        productRepository.delete(id);
        return new ResponseEntity(id, HttpStatus.ACCEPTED);
    }

    public List<Product> getAllProductsBypassAccessRights(String organizationId) {
        return productRepository.getProductsByOrganizationId(organizationId);
    }

    public Product getProductBypassAccessRights(String id) {
        return productRepository.get(id);

    }
}
