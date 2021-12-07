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
    public List<String> validateCupon(Cupon cupon) {
        Map<String, Float> precioItems = itemService.construirItems(cupon.item_ids);
        return calculate(precioItems, cupon.amount);
    }

    public List<String> calculate(Map<String, Float> items, Float amount){

        Float valorFinal =  items.values().stream().reduce(0.00f,Float::sum);

        if (valorFinal > amount){
            Map<String, Float> itemsNuevos = eliminarItemMayor(items);
            calculate(itemsNuevos,amount);
        }
        return new ArrayList(items.keySet());
    }


    public Map<String, Float> eliminarItemMayor(Map<String, Float> items){
        Float maxValueInMap=(Collections.max(items.values()));
         for (Map.Entry<String, Float> entry : items.entrySet()) {
             if (Objects.equals(entry.getValue(), maxValueInMap)) {
                 items.remove(entry.getKey());
             }
         }
    return items;
    }

}
