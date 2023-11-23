package com.coffeedev.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.coffeedev.admin.order.OrderRepository;
import com.coffeedev.common.entity.Customer;
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
	public void testAddNewOrder() {
		Customer customer = entityManager.find(Customer.class, 23);
		Product product = entityManager.find(Product.class, 5);

		Order mainOrder = new Order();
		mainOrder.setCustomer(customer);
		mainOrder.setName(customer.getName());
		mainOrder.setPhoneNumber(customer.getPhoneNumber());;
		mainOrder.setAddress(customer.getAddress());

		mainOrder.setShippingCost(10);
		mainOrder.setSubtotal(product.getPrice());
		mainOrder.setTotal(product.getPrice()+10);

		mainOrder.setPaymentMethod(PaymentMethod.CHUYENKHOAN);
		mainOrder.setOrderStatus(OrderStatus.PICKED);
		mainOrder.setDeliverDate(new Date());

		OrderDetail orderDetail= new OrderDetail();
		orderDetail.setProduct(product);
		orderDetail.setOrder(mainOrder);
		orderDetail.setShippingCost(10);
		orderDetail.setQuantity(3);
		orderDetail.setSubtotal(product.getPrice());
		orderDetail.setUnitPrice(product.getPrice());

		mainOrder.getOrderDetails().add(orderDetail);
		Order savedOrder=repo.save(mainOrder);
		assertThat(savedOrder.getId()).isGreaterThan(0);
	}
}
