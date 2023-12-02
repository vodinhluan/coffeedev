package com.coffeedev.order;

import org.springframework.data.repository.CrudRepository;

import com.coffeedev.common.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>  {

}
