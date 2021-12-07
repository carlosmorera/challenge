package com.challenge.cupon.controller;


import com.challenge.cupon.apirest.entity.Cupon;
import com.challenge.cupon.apirest.service.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST})
@RequestMapping("")
public class UserRestController {

    @Autowired
    private CuponService cuponService;

    @PostMapping("/cupon")
    public List<String> validateCupon(@RequestBody Cupon cupon) {
         return cuponService.validateCupon(cupon);
    }

}