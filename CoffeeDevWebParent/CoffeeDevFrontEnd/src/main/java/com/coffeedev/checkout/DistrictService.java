package com.coffeedev.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;
    
    public double getShippingPriceForDistrict(String districtName) {
        Double district = districtRepository.findPriceByName(districtName);
        return (district != null) ? district : 0.0;
    }

}
