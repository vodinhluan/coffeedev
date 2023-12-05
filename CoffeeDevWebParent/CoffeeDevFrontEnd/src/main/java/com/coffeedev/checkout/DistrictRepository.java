package com.coffeedev.checkout;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.coffeedev.common.entity.District;

public interface DistrictRepository extends CrudRepository<District, Integer> {
	public List<District> findAllByOrderByNameAsc();
	
	@Query("SELECT d.price FROM District d WHERE d.name = :name")
	Double findPriceByName(@Param("name") String name);
	
    District findByName(String name);

}
