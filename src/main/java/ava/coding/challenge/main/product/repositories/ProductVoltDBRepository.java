package ava.coding.challenge.main.product.repositories;

import ava.coding.challenge.main.product.entities.Product;
import ava.coding.challenge.repository.IRepository;
import org.springframework.stereotype.Repository;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProductVoltDBRepository extends IRepository<Product> {
    String driver = "org.voltdb.jdbc.Driver";
    String url = "avabackup.northeurope.cloudapp.azure.com:21212";
    Client client = null;
    ClientConfig config = null;

    public ProductVoltDBRepository() throws IOException {
        config = new ClientConfig("usr", "qlZ6rZT1");
        client = ClientFactory.createClient(config);
        client.createConnection(url);
    }

    @Override
    public List<Product> getAll() {
        VoltTable voltDBProductList;
        ArrayList<Product> productList = new ArrayList<>();
        try {
            voltDBProductList = client.callProcedure("getAllProducts").getResults()[0];
            voltDBProductList.resetRowPosition();
            while(voltDBProductList.advanceRow()) {
                voltDBProductList.resetRowPosition();
                productList.add(initializeProduct(voltDBProductList));
            }
            return productList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public Product get(String id) {
        VoltTable voltDBProduct;
        Product product = new Product();
        try {
            voltDBProduct = client.callProcedure("getProduct", id).getResults()[0];
            voltDBProduct.resetRowPosition();
            while (voltDBProduct.advanceRow()) {
                product = initializeProduct(voltDBProduct);
            }
            return product;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    public List<Product> getProductsByOrganizationId(String organizationId) {
        VoltTable voltDBProductList;
        ArrayList<Product> productList = new ArrayList<>();
        try {
            voltDBProductList = client.callProcedure("getProductsByOrganizationId", organizationId).getResults()[0];
            voltDBProductList.resetRowPosition();
            while (voltDBProductList.advanceRow()) {
                productList.add(initializeProduct(voltDBProductList));
            }
            return productList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void add(Product newEntity) {
        try {
            if(newEntity.getId() == null) {
                newEntity.setId(UUID.randomUUID().toString());
            }
            client.callProcedure("PRODUCTS.insert",
                    newEntity.getId(), newEntity.getOrganization(), newEntity.getName(),
                    newEntity.getPrice(), newEntity.getStock());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void update(String id, Product updatedEntity) {
        try {
            client.callProcedure("PRODUCTS.update",
                    updatedEntity.getId(), updatedEntity.getOrganization(), updatedEntity.getName(),
                    updatedEntity.getPrice(), updatedEntity.getStock(), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    @Override
    public void delete(String id) {
        try {
            client.callProcedure("PRODUCTS.delete", id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }

    private Product initializeProduct(VoltTable voltDBProduct) {
        Product product = new Product();
        product.setId((String) voltDBProduct.get("ProductId", VoltType.STRING));
        product.setName((String) voltDBProduct.get("Name", VoltType.STRING));
        product.setOrganization((String) voltDBProduct.get("OrganizationId", VoltType.STRING));
        product.setPrice((double) voltDBProduct.get("Price", VoltType.FLOAT));
        product.setStock((int) voltDBProduct.get("Stock", VoltType.INTEGER));
        return product;
    }
}
