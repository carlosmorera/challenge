package com.challenge.cupon.apirest.service;


import com.challenge.cupon.apirest.entity.Item;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ItemService {
    public Map<String, Float> construirItems(List<String> itemsToSearch);
    public Map<String, Float>  obtenerItemsRedis(Set<String>itemsToSearch);
}
