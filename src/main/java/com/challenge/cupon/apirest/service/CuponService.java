package com.challenge.cupon.apirest.service;

import com.challenge.cupon.apirest.entity.Cupon;

import java.util.List;

public interface CuponService {
    public List<String> validateCupon(Cupon cupon);
}
