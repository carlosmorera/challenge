package com.challenge.cupon.apirest.entity;


import java.util.List;


public class Cupon {
    public List<String> item_ids;

    public Float amount;
    public Cupon(List<String> item_ids, Float amount) {
        this.item_ids = item_ids;
        this.amount = amount;
    }


}
