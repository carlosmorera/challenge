package com.challenge.cupon.apirest.service;

import com.challenge.cupon.apirest.repository.ItemRepositoryImpl;
import com.challenge.cupon.apirest.entity.Item;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import redis.embedded.RedisServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemServiceImplTest {

    @Autowired
    private RedisTemplate<String, Float> redisTemplate;

    private RedisServer redisServer;

    @BeforeClass
    public void setUpClass() throws Exception {
        redisServer = new RedisServer(6350);
        redisTemplate.setEnableTransactionSupport(true);
        redisServer.start();
    }

    @AfterClass
    public void tearDownClass() {
        redisServer.stop();
    }

    @Test
    public void shouldElementsOfList(){
        List items = new ArrayList();
        items.add("MLA844702267");
        items.add("MLA844702264");
        this.redisTemplate.opsForHash().put("Item", "MLA844702267",10.22f);
        ItemRepositoryImpl itemDAO = new ItemRepositoryImpl(this.redisTemplate);
        ItemServiceImpl service = new ItemServiceImpl(itemDAO);

        Map<String, Float> map = service.buildItems(items);

        assertEquals(true,!map.isEmpty());
        assertEquals(1010.22f,map.values().stream().reduce(0.00f, Float::sum));
    }
    @Test
    public void shouldGetSumOfiTemsOfRedis(){
        List items = new ArrayList();
        items.add("a");
        items.add("b");
        items.add("c");
        Item itemA = new Item("a", 10.22f);
        Item ItemB = new Item("b", 8f);
        ItemRepositoryImpl itemDAO = new ItemRepositoryImpl(this.redisTemplate);
        ItemServiceImpl service = new ItemServiceImpl(itemDAO);
        itemDAO.save(itemA);
        itemDAO.save(ItemB);
        Float total = service.sumItems(items);
        assertEquals(true,total >0 );
    }
}
