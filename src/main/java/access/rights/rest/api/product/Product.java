package access.rights.rest.api.product;

public class Product {

    public int id;
    public String name;
    public double price;
    public int stockAvailability;

    public Product(int id, String name, double price, int stockAvailability) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockAvailability = stockAvailability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getStockAvailability() {
        return stockAvailability;
    }

    public void setStockAvailability(int stockAvailability) {
        this.stockAvailability = stockAvailability;
    }
}
