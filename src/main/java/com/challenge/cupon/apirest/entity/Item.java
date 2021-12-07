package com.challenge.cupon.apirest.entity;

public class Item {
    private String id;
    private Float price;

    public Item(String id, Float price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id=id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
