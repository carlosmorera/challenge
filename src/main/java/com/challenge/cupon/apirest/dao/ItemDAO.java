package com.challenge.cupon.apirest.dao;

import com.challenge.cupon.apirest.entity.Item;

import java.util.Map;

public interface ItemDAO {

    public Map<String, Float> findAll();

    public void save(Item item);
}
