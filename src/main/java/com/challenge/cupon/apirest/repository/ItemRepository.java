package com.challenge.cupon.apirest.repository;

import com.challenge.cupon.apirest.entity.Item;

import java.util.Map;

public interface ItemRepository {

    public Map<String, Float> findAll();

    public void save(Item item);
}
