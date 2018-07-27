package access.rights.rest.api.product;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    public Integer id;
    public String name;
    public double price;
    public int stock;

    public Product() { }
    public Product(Integer id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
