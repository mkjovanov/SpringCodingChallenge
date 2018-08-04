package access.rights.rest.api.product.repository;

import access.rights.rest.api.employee.Employee;
import access.rights.rest.api.product.Product;
import access.rights.rest.api.repository.IRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductInMemoryRepository extends IRepository<Product> {

    public List<Product> productList = new ArrayList<>(Arrays.asList(
            new Product(1, "Elektromotor", 550, 5, 2),
            new Product(2, "Energetski kabel", 100, 8, 2),
            new Product(3, "Osigurač", 20, 120, 2),
            new Product(4, "Kućište", 200, 20, 2),
            new Product(5, "Veš mašina", 2000, 5, 3),
            new Product(6, "Bojler", 500, 4, 3),
            new Product(7, "Šporet", 1200, 2, 3),
            new Product(7, "Sudo mašina", 800, 7, 3)));

    @Override
    public List<Product> getAll() {
        return productList;
    }

    public List<Product> getAllByOrganizationId(Integer organizationId) {
        return productList.stream().filter(e -> e.getOrganization().getId() == organizationId).collect(Collectors.toList());
    }

    @Override
    public Product get(Integer id) {
        return productList.stream().filter(p -> p.getId() == id).findFirst().get();
    }

    @Override
    public void add(Product newProduct) {
        productList.add(newProduct);
    }

    @Override
    public void update(Product updatedProduct) {
        for(int i = 0; i < productList.size(); i++) {
            Product e = productList.get(i);
            if(e.getId() == updatedProduct.getId()) {
                productList.set(i, updatedProduct);
                return;
            }
        }
    }

    @Override
    public void delete(Integer id) {
        productList.removeIf(p -> p.getId() == id);
    }
}
