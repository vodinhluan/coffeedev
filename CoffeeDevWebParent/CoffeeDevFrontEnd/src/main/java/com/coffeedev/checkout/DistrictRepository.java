package com.coffeedev.checkout;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.coffeedev.common.entity.District;

public interface DistrictRepository extends CrudRepository<District, Integer> {
	public List<District> findAllByOrderByNameAsc();
}
