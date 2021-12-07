package com.challenge.cupon.apirest.service;

import com.challenge.cupon.apirest.entity.Cupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CuponServiceImpl implements CuponService {

    @Autowired
    private ItemService itemService;

    @Override
    public Cupon validateCupon(Cupon cupon) {
        Map<String, Float> precioItems = itemService.buildItems(cupon.item_ids);
        List<String> items = calculate(precioItems, cupon.amount);
        Float total = itemService.sumItems(items);
        return new Cupon(items, total);
    }

    public List<String> calculate(Map<String, Float> items, Float amount) {
        Float valorFinal = items.values().stream().reduce(0.00f, Float::sum);
        if (valorFinal > amount) {
            Map<String, Float> itemsNuevos = eliminarItemMayor(items);
            calculate(itemsNuevos, amount);
        }
        return new ArrayList(items.keySet());
    }

    public Map<String, Float> eliminarItemMayor(Map<String, Float> items) {
        Float maxValueInMap = (Collections.max(items.values()));
        items.values().removeIf(value -> Float.compare(value, maxValueInMap) == 0);
        return items;
    }

}
