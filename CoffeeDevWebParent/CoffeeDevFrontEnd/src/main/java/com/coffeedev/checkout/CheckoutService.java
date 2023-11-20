package com.coffeedev.checkout;

import java.util.List;

import org.springframework.stereotype.Service;
import com.coffeedev.common.entity.District;

@Service
public class CheckoutService {
    private final DistrictRepository districtRepository;

    public CheckoutService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public List<District> getAllDistricts() {
        return districtRepository.findAllByOrderByNameAsc();
    }
}

