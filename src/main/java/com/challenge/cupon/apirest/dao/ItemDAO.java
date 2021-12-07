package com.challenge.cupon.apirest.dao;

import com.challenge.cupon.apirest.entity.Item;

import java.util.List;

public interface ItemDAO {

    public List<Item> findAll();

    public Item findById(String id);

    public void save(Item item);

    public void deleteById(String id);
}
