package com.productos.product.entitys;

import com.productos.product.ProductApplication;
import jakarta.persistence.*;

@Table(name="product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name="NAME")
    private String name;
    @Column(name="PRICE")
    private double price;

    public Product(){

    }

    public Product(String name, Long id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
