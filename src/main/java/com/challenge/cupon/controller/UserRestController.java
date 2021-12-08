package com.challenge.cupon.controller;

import com.challenge.cupon.apirest.entity.Cupon;
import com.challenge.cupon.apirest.service.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
@RequestMapping("")
public class UserRestController {

    @Autowired
    private CuponService cuponService;

    @PostMapping("/cupon")
    public ResponseEntity<Cupon> validateCupon(@RequestBody Cupon cupon) {

        Cupon cuponResponse = cuponService.validateCupon(cupon);

        return cuponResponse.item_ids.size() == 0
                ? new ResponseEntity<>(cuponResponse, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(cuponResponse, HttpStatus.OK);

    }

}
