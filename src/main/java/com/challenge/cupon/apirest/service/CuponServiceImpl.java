package com.challenge.cupon.apirest.service;

import com.challenge.cupon.apirest.entity.Cupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CuponServiceImpl implements CuponService {

    private ItemService itemService;

    public CuponServiceImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public Cupon validateCupon(Cupon cupon) {
        Map<String, Float> priceItems = itemService.buildItems(cupon.item_ids);
        List<String> items = calculate(priceItems, cupon.amount);
        Float total = itemService.sumItems(items);
        return new Cupon(items, total);
    }

    public List<String> calculate(Map<String, Float> items, Float amount) {
        Float total = items.values().stream().reduce(0.00f, Float::sum);
        if (total > amount) {
            Map<String, Float> newItems = deleteItemWithMayorPrice(items);
            calculate(newItems, amount);
        }
        return new ArrayList(items.keySet());
    }

    public Map<String, Float> deleteItemWithMayorPrice(Map<String, Float> items) {
        Float priceMayor = (Collections.max(items.values()));
        items.values().removeIf(value -> Float.compare(value, priceMayor) == 0);
        return items;
    }

}
