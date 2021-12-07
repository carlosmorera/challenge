package com.challenge.cupon.apirest.service;


import com.challenge.cupon.apirest.dao.ItemDAO;
import com.challenge.cupon.apirest.dao.ItemDAOImpl;
import com.challenge.cupon.apirest.entity.Item;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
@Transactional
public class ItemServiceImpl implements ItemService{

    @Resource
    private ItemDAO itemDAO;

    public ItemServiceImpl(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }
    @Override
    public Map<String, Float> construirItems(List<String> itemsConsulta) {

       Set<String> itemsUnicos  = new LinkedHashSet<>(itemsConsulta);
        Map<String, Float> itemsEncontrados = obtenerItemsRedis(itemsUnicos);
        itemsEncontrados.keySet().forEach(itemsUnicos::remove);
        Map<String, Float> newItems = itemsUnicos.stream()
                .map(idItem -> consultarItem("https://api.mercadolibre.com/items/" + idItem))
                .map(CompletableFuture::join).map(itemJson -> {
                    Gson gson = new Gson();
                    return gson.fromJson(itemJson, Item.class);
                }).collect(Collectors.toMap(Item::getId, Item::getPrice));

         itemsEncontrados.putAll(newItems);
         return itemsEncontrados;
    }

    @Override
    public  Map<String, Float>  obtenerItemsRedis(Set<String> itemsToSearch) {
        List<Item> itemsRedis = itemDAO.findAll();
        Map<String, Float> collect = itemsToSearch.stream()
                .flatMap(itemId -> itemsRedis.stream().filter(item -> item.getId().equals(itemId))).collect(Collectors.toMap(Item::getId, Item::getPrice));
////esto son logs basura
        System.out.printf("redis:"+collect);
        collect.values().stream().map(i -> System.out.printf("elemento:"+i));
        return collect;
    }

    public  CompletableFuture<String> consultarItem(String url) {
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
