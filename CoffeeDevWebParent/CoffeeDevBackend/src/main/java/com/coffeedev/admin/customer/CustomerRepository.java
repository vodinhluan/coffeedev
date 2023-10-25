package com.coffeedev.admin.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.coffeedev.admin.paging.SearchRepository;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.entity.User;

public interface CustomerRepository extends SearchRepository<Customer, Integer>, CrudRepository<Customer, Integer> {
	
	@Query("SELECT c FROM Customer c WHERE CONCAT(c.email, ' ', c.name, ' ', "
			+ "c.address) LIKE %?1%")
	public Page<Customer> findAll(String keyword, Pageable pageable);

	@Query("UPDATE Customer c SET c.enabled = ?2 WHERE c.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);

	@Query("SELECT c FROM Customer c WHERE c.email = ?1")
	public Customer findByEmail(String email);

	public Long countById(Integer id);	
}
