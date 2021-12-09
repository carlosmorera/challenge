package com.challenge.cupon.apirest.service;

import com.challenge.cupon.apirest.entity.Cupon;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CuponServiceImplTest {

    @Mock
    ItemServiceImpl itemService;

    @Test
    public void shouldElementsOfList(){
        List items = new ArrayList();
        items.add("MLA844702267");
        items.add("MLA844702264");
        Cupon cupon = new Cupon(items, 100f);

        HashMap<String, Float> itemsWithPrice = new HashMap<>();
        itemsWithPrice.put("MLA844702267", 10000f);
        itemsWithPrice.put("MLA844702264",9.88f);
        when(itemService.buildItems(items)).thenReturn(itemsWithPrice);
        when(itemService.sumItems(items)).thenReturn(9.00f);

        CuponServiceImpl service = new CuponServiceImpl(itemService);

        Cupon cuponFinal = service.validateCupon(cupon);

        assertEquals(true,!cuponFinal.item_ids.isEmpty());
    }
}
