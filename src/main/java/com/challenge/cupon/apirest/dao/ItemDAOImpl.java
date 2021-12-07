package com.challenge.cupon.apirest.dao;

import com.challenge.cupon.apirest.entity.Item;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class ItemDAOImpl implements ItemDAO {
    private static final String KEY = "Item";

    private RedisTemplate<String, Float> redisTemplate;
    private HashOperations hashOperations;

    public ItemDAOImpl(RedisTemplate<String, Float> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }
    @Override
    public Map<String, Float> findAll() {
        return  hashOperations.entries(KEY);
    }

    @Override
    public Item findById(String id) {
        return (Item) hashOperations.get(KEY, id);
    }

    @Override
    public void save(Item item) {
        hashOperations.put(KEY, item.getId(), item.getPrice());
    }

    @Override
    public void deleteById(String id) {
        hashOperations.delete(KEY, id);
    }
}
