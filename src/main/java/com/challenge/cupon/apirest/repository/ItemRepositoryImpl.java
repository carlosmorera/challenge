package com.challenge.cupon.apirest.repository;

import com.challenge.cupon.apirest.entity.Item;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private static final String KEY = "Item";

    private RedisTemplate<String, Float> redisTemplate;
    private HashOperations hashOperations;

    public ItemRepositoryImpl(RedisTemplate<String, Float> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();

    }

    @Override
    public Map<String, Float> findAll() {
        return  hashOperations.entries(KEY);
    }

    @Override
    public void save(Item item) {
        hashOperations.put(KEY, item.getId(), item.getPrice());
    }

}
