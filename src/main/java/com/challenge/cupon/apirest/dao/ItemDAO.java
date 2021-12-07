package com.challenge.cupon.apirest.dao;

import com.challenge.cupon.apirest.entity.Item;

import java.util.Map;

public interface ItemDAO {

    public Map<String, Float> findAll();

    public Item findById(String id);

    public void save(Item item);

    public void deleteById(String id);
}
