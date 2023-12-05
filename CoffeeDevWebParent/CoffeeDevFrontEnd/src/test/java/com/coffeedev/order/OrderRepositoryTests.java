package com.coffeedev.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.coffeedev.common.entity.CartItem;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.entity.District;
import com.coffeedev.common.entity.Order;
import com.coffeedev.common.entity.OrderDetail;
import com.coffeedev.common.entity.OrderStatus;
import com.coffeedev.common.entity.PaymentMethod;
import com.coffeedev.common.entity.Product;
import com.coffeedev.shoppingcart.CartItemRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class OrderRepositoryTests {

	@Autowired private OrderRepository repo;
	@Autowired private CartItemRepository cartRepo;

	@Autowired private TestEntityManager entityManager;

	@Test
	public void testCreateNewOrderWithMultipleProducts() {
		Customer customer = entityManager.find(Customer.class, 27);
		Product product1 = entityManager.find(Product.class, 16);
		Product product2 = entityManager.find(Product.class, 1);
		Product product3 = entityManager.find(Product.class, 14);


		District district = entityManager.find(District.class, 3);
		CartItem cart = entityManager.find(CartItem.class, 35);
		List<CartItem> cartItems = cartRepo.findByCustomer(customer);
		float estimatedTotal = 0.0F;
		for (CartItem item : cartItems) {
			estimatedTotal += item.getSubtotal();
		}
		
		Order mainOrder = new Order();
		mainOrder.setCustomer(customer);
		mainOrder.setName(customer.getName());
		mainOrder.setAddress(customer.getAddress());
		mainOrder.setPhoneNumber("0876546331");
		mainOrder.setOrderTime(new Date());
		mainOrder.setDistrict(district.getName()); 
		mainOrder.setTotalCost((double) (estimatedTotal+cart.getShippingCost()));
		mainOrder.setPaymentMethod(PaymentMethod.COD);
		mainOrder.setOrderStatus(OrderStatus.PICKED);
		
		OrderDetail orderDetail1 = new OrderDetail();
		orderDetail1.setProduct(product1);
		orderDetail1.setOrder(mainOrder);
		orderDetail1.setProductCost(product1.getPrice());
		orderDetail1.setQuantity(2);
		orderDetail1.setSubtotalCost(product1.getPrice() * 2);
		orderDetail1.setShippingCost((double) 10);

		
		OrderDetail orderDetail2 = new OrderDetail();
		orderDetail2.setProduct(product2);
		orderDetail2.setOrder(mainOrder);
		orderDetail2.setProductCost(product2.getPrice());
		orderDetail2.setQuantity(3);
		orderDetail2.setSubtotalCost(product2.getPrice());
		orderDetail2.setShippingCost((double) 10);
		
		OrderDetail orderDetail3 = new OrderDetail();
		orderDetail3.setProduct(product3);
		orderDetail3.setOrder(mainOrder);
		orderDetail3.setProductCost(product3.getPrice());
		orderDetail3.setQuantity(2);
		orderDetail3.setSubtotalCost(product3.getPrice());
		orderDetail3.setShippingCost((double) 10);

		
		mainOrder.getOrderDetails().add(orderDetail1);
		mainOrder.getOrderDetails().add(orderDetail2);
		mainOrder.getOrderDetails().add(orderDetail3);

		
		Order savedOrder = repo.save(mainOrder);
		
		assertThat(savedOrder.getId()).isGreaterThan(0);		
	}
	
	@Test
	public void testListOrders() {
		Iterable<Order> orders = repo.findAll();
		
		assertThat(orders).hasSizeGreaterThan(0);
		
		orders.forEach(System.out::println);
	}
	
}