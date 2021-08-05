package com.shop.diamond.item.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    private Long price;

    private String name;

    private String imagePath;

    private String description;

    private Long stock;

    private boolean sale;

    private Brand brand;

    public Item() {
    }

    public Item(Long price, String name, String imagePath, String description, Long stock, boolean sale, Brand brand) {
        this.price = price;
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
        this.stock = stock;
        this.sale = sale;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }


    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", sale=" + sale +
                ", brand=" + brand +
                '}';
    }
}
