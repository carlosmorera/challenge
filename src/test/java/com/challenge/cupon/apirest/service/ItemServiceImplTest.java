package com.challenge.cupon.apirest.service;

import com.challenge.cupon.apirest.dao.ItemDAOImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemServiceImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RedisTemplate<String, Float> redisTemplate;

    private RedisServer redisServer;

    private final ItemDAOImpl ItemDAO = new ItemDAOImpl(redisTemplate);
    private final ItemServiceImpl service = new ItemServiceImpl(ItemDAO);

    @Before
    public void setUp() throws IOException {
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @After
    public void tearDown() {
        redisServer.stop();
    }

    @Test
    public void obtenerSuma(){

        List items = new ArrayList();
        items.add("a");
        items.add("b");
        items.add("c");
        HashOperations hashOperations = this.redisTemplate.opsForHash();
        try {
            hashOperations.put("Item", "a",10.22f);
        } catch (Exception e) {
            System.out.printf("suma: ");
        }

        System.out.printf("suma: "+hashOperations.entries("Item"));

    }
}
