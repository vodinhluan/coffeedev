package com.coffeedev.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeedev.checkout.CheckoutInfo;
import com.coffeedev.common.entity.CartItem;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.entity.District;
import com.coffeedev.common.entity.Order;
import com.coffeedev.common.entity.OrderDetail;
import com.coffeedev.common.entity.OrderStatus;
import com.coffeedev.common.entity.PaymentMethod;
import com.coffeedev.common.entity.Product;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;

	public Order createOrder(Customer customer, District district, List<CartItem> cartItems,
			PaymentMethod paymentMethod, float estimatedTotal) {
		Order newOrder = new Order();
		newOrder.setCustomer(customer);
		newOrder.setName(customer.getName());
		newOrder.setAddress(customer.getAddress());
		newOrder.setPhoneNumber(customer.getPhoneNumber());
		newOrder.setOrderTime(new Date());
		newOrder.setDistrict(district.getName()); 
		newOrder.setTotalCost((double) 10000);
		newOrder.setPaymentMethod(paymentMethod);
		newOrder.setOrderStatus(OrderStatus.NEW);
		Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
		Double subtotal = 0.0;  
		for (CartItem cartItem : cartItems) {
			Product productInCart = cartItem.getProduct();
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(newOrder);
			orderDetail.setProduct(productInCart);
			orderDetail.setQuantity(cartItem.getQuantity());
			orderDetail.setProductCost(productInCart.getPrice() * cartItem.getQuantity());
			orderDetail.setSubtotalCost(cartItem.getSubtotal());
			subtotal += orderDetail.getSubtotalCost();
			orderDetails.add(orderDetail);
		}

		for (CartItem item : cartItems) {
			estimatedTotal += item.getSubtotal();
		}
		newOrder.setTotalCost((double) estimatedTotal);
		return repo.save(newOrder);	}

	


	

	
	
}