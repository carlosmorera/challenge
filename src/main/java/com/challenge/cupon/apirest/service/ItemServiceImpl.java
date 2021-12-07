package com.challenge.cupon.apirest.service;


import com.challenge.cupon.apirest.dao.ItemDAO;
import com.challenge.cupon.apirest.entity.Item;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemServiceImpl implements ItemService{

    private ItemDAO itemDAO;

    public ItemServiceImpl(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }
    @Override
    public Map<String, Float> buildItems(List<String> itemsConsulta) {

       Set<String> uniqueItems  = new LinkedHashSet<>(itemsConsulta);
        Map<String, Float> itemsFound = getItemsRedis(uniqueItems);
        itemsFound.keySet().forEach(uniqueItems::remove);
        Map<String, Float> newItems = uniqueItems.stream()
                .map(idItem -> getItem("https://api.mercadolibre.com/items/" + idItem))
                .map(CompletableFuture::join).map(itemJson -> {
                    Gson gson = new Gson();
                    Item item = gson.fromJson(itemJson, Item.class);
                    itemDAO.save(item);
                    return item;
                }).collect(Collectors.toMap(Item::getId, Item::getPrice));
         itemsFound.putAll(newItems);
         return itemsFound;
    }

    @Override
    public Float sumItems(List<String> itemsToSum) {
        return getItemsRedis(new LinkedHashSet<>(itemsToSum)).values()
                .stream()
                .reduce(0.00f, Float::sum);
    }

    private Map<String, Float> getItemsRedis(Set<String> itemsBusqueda) {
        Map<String, Float> itemsRedis = itemDAO.findAll();
        Map<String, Float> itemsFiltrados = new HashMap<>();
        itemsRedis.entrySet().forEach(items -> {
            itemsBusqueda.forEach(x -> {
                if (items.getKey().contains(x)) {
                    itemsFiltrados.put(items.getKey(), items.getValue());
                }
            });
        });
        return itemsFiltrados;
    }

    private  CompletableFuture<String> getItem(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
