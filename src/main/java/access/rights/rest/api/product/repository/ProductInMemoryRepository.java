package access.rights.rest.api.product.repository;

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
            new Product("test1", "test", 1, 1, "a"),
            new Product("elektromotor", "Elektromotor", 550, 5, "b"),
            new Product("energetski-kabel", "Energetski kabel", 100, 8, "b"),
            new Product("osigurac", "Osigurač", 20, 120, "b"),
            new Product("kuciste", "Kućište", 200, 20, "b"),
            new Product("ves-masina", "Veš mašina", 2000, 5, "c"),
            new Product("bojler", "Bojler", 500, 4, "c"),
            new Product("sporet", "Šporet", 1200, 2, "c"),
            new Product("sudo-masina", "Sudo mašina", 800, 7, "c")));

    @Override
    public List<Product> getAll() {
        return productList;
    }

    public List<Product> getAllByOrganizationId(String organizationId) {
        return productList.stream().filter(e -> e.getOrganization().getId().equals(organizationId)).collect(Collectors.toList());
    }

    @Override
    public Product get(String id) {
        return productList.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    }

    @Override
    public void add(Product newProduct) {
        productList.add(newProduct);
    }

    @Override
    public void update(String id, Product updatedProduct) {
        for(int i = 0; i < productList.size(); i++) {
            Product p = productList.get(i);
            if(p.getId().equals(id)) {
                productList.set(i, updatedProduct);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        productList.removeIf(p -> p.getId().equals(id));
    }
}
