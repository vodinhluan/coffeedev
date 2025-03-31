package com.coffeedev.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.coffeedev.admin.order.OrderRepository;
import com.coffeedev.common.entity.CartItem;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.entity.District;
import com.coffeedev.common.entity.Order;
import com.coffeedev.common.entity.OrderDetail;
import com.coffeedev.common.entity.OrderStatus;
import com.coffeedev.common.entity.PaymentMethod;
import com.coffeedev.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class OrderRepositoryTests {

	@Autowired private OrderRepository repo;

	@Autowired private TestEntityManager entityManager;

	@Test
	public void testCreateNewOrderWithSingleProduct() {
		Customer customer = entityManager.find(Customer.class, 29);
		Product product = entityManager.find(Product.class, 16);
		District district = entityManager.find(District.class, 16);
		CartItem cart = entityManager.find(CartItem.class, 13);

		Order mainOrder = new Order();
		mainOrder.setCustomer(customer);
		mainOrder.setName(customer.getName());
		mainOrder.setAddress(customer.getAddress());
		mainOrder.setPhoneNumber("0909123456");
		mainOrder.setOrderTime(new Date());
		mainOrder.setDistrict(district.getName()); 
		mainOrder.setTotalCost(cart.getSubtotal()+cart.getShippingCost());
		mainOrder.setPaymentMethod(PaymentMethod.COD);
		mainOrder.setOrderStatus(OrderStatus.PAID);
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setProduct(product);
		orderDetail.setOrder(mainOrder);
		orderDetail.setProductCost(product.getPrice());
		orderDetail.setShippingCost((double) 10);
		orderDetail.setQuantity(2);
		orderDetail.setSubtotalCost(product.getPrice());
		
		mainOrder.getOrderDetails().add(orderDetail);
		
		Order savedOrder = repo.save(mainOrder);
		
		assertThat(savedOrder.getId()).isGreaterThan(0);		
	}
	
}
