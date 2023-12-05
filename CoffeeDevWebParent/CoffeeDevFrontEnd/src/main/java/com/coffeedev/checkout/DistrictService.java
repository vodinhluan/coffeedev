package com.coffeedev.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeedev.common.entity.District;


@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;
    
    public double getShippingPriceForDistrict(String districtName) {
        Double district = districtRepository.findPriceByName(districtName);
        return (district != null) ? district : 0.0;
    }

    public District getDistrictByName(String districtName) {
        return districtRepository.findByName(districtName);
    }

    public District saveDistrict(District district) {
        return districtRepository.save(district);
    }

    public District getOrCreateDistrictByName(String districtName) {
        District district = getDistrictByName(districtName);
        if (district == null) {
            district = new District();
            district.setName(districtName);
            district = saveDistrict(district);
        }
        return district;
    }

   

}
