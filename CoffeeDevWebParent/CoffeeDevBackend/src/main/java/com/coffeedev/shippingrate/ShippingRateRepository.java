package com.coffeedev.shippingrate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.coffeedev.admin.paging.SearchRepository;
import com.coffeedev.common.entity.ShippingRate;

public interface ShippingRateRepository extends SearchRepository<ShippingRate, Integer> {

	@Query("SELECT sr FROM ShippingRate sr WHERE sr.district.id = ?1 AND sr.ward = ?2")
	public ShippingRate findByCountryAndState(Integer districtId, String ward);

	@Query("UPDATE ShippingRate sr SET sr.codSupported = ?2 WHERE sr.id = ?1")
	@Modifying
	public void updateCODSupport(Integer id, boolean enabled);

	@Query("SELECT sr FROM ShippingRate sr WHERE sr.district.name LIKE %?1% OR sr.ward LIKE %?1%")
	public Page<ShippingRate> findAll(String keyword, Pageable pageable);

	public Long countById(Integer id);
}
