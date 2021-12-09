package com.challenge.cupon;

import com.challenge.cupon.apirest.entity.Cupon;
import com.challenge.cupon.apirest.service.CuponServiceImpl;
import com.challenge.cupon.controller.UserRestController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CuponApplicationTests {
    @Mock
    CuponServiceImpl cuponService;
    @Autowired
    UserRestController userRestController;

    @Test
    void shouldCuponSuccess() {
        List items = new ArrayList();
        items.add("MLA844702267");
        items.add("MLA844702264");
        Cupon cupon = new Cupon(items, 100f);

        when(cuponService.validateCupon(cupon)).thenReturn(cupon);
        ResponseEntity<Cupon> response = userRestController.validateCupon(cupon);

        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    void shouldCuponfail() {
        List items = new ArrayList();
        Cupon cupon = new Cupon(items, 0f);

        when(cuponService.validateCupon(cupon)).thenReturn(cupon);
        ResponseEntity<Cupon> response = userRestController.validateCupon(cupon);

        assertEquals(404,response.getStatusCodeValue());
    }

}
