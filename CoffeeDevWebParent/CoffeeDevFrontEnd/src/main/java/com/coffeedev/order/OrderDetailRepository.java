package com.coffeedev.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coffeedev.common.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    @Modifying
    @Query("UPDATE OrderDetail od SET od.shippingCost = :shippingCost WHERE od.id = :orderDetailId")
    void updateShippingCost(@Param("orderDetailId") Integer orderDetailId, @Param("shippingCost") Double shippingCost);
}
