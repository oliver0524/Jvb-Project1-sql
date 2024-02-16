package org.example.Model;

import java.util.Objects;

/** Class ProductInfo */
public class ProductInfo {
    private long id;
    private String name;
    private double price;
    private String sellername;

    // Constructor for the ProductInfo class
    public ProductInfo(long id, String name, double price, String sellername) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sellername = sellername;
    }

    public ProductInfo(){
    }

    /** Setters and getters for the ProductInfo class variables. No usage annotations are ok */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductInfo)) return false;
        ProductInfo that = (ProductInfo) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /** This code is used to convert ProductInfo objects into a String (displayable format) */
    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", sellername='" + sellername + '\'' +
                '}';
    }
}

