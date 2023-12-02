package com.coffeedev.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping("/{name}")
    public ResponseEntity<Double> getShippingPrice(@PathVariable String name) {
        double shippingPrice = districtService.getShippingPriceForDistrict(name);
        return ResponseEntity.ok(shippingPrice);
    }
}

