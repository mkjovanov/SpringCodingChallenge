package ava.coding.challenge.main.product.repositories;

import ava.coding.challenge.main.product.entities.Product;
import ava.coding.challenge.repository.IRepository;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                Product product = new Product();
                product.setId((String) voltDBProductList.get("ProductId", VoltType.STRING));
                product.setName((String) voltDBProductList.get("Name", VoltType.STRING));
                product.setOrganization((String) voltDBProductList.get("OrganizationId", VoltType.STRING));
                product.setPrice((float) voltDBProductList.get("Price", VoltType.FLOAT));
                product.setStock((int) voltDBProductList.get("Stock", VoltType.INTEGER));
                productList.add(product);
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
                product.setId((String) voltDBProduct.get("ProductId", VoltType.STRING));
                product.setName((String) voltDBProduct.get("Name", VoltType.STRING));
                product.setOrganization((String) voltDBProduct.get("OrganizationId", VoltType.STRING));
                product.setPrice((float) voltDBProduct.get("Price", VoltType.FLOAT));
                product.setStock((int) voltDBProduct.get("Stock", VoltType.INTEGER));
            }
            return product;
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
            client.callProcedure("PRODUCT.insert",
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
            client.callProcedure("PRODUCT.insert",
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
            client.callProcedure("PRODUCT.delete", id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //client.drain();
            //client.close();
        }
    }
}
