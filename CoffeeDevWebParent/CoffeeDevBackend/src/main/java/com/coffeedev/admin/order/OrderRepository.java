package com.coffeedev.admin.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.coffeedev.admin.paging.SearchRepository;
import com.coffeedev.common.entity.Order;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer>, CrudRepository<Order, Integer>, SearchRepository<Order, Integer> {
	@Query("SELECT o FROM Order o WHERE o.name LIKE %?1% OR"
			+ " o.phoneNumber LIKE %?1% OR"
			+ " o.address LIKE %?1% OR"
			+ " o.paymentMethod LIKE %?1% OR o.orderStatus LIKE %?1% OR"
			+ " o.customer.name LIKE %?1%")
	
	public Page<Order> findAll(String keyword, Pageable pageable);
	
	public Long countById(Integer id);

}