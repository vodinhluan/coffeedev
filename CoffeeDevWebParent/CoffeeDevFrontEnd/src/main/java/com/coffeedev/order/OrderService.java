package com.coffeedev.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeedev.checkout.CheckoutInfo;
import com.coffeedev.checkout.DistrictService;
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
	@Autowired
	private DistrictService districtService;

	public Order createOrder(Customer customer, List<CartItem> cartItems,  float estimatedTotal) {
		Order newOrder = new Order();
		newOrder.setCustomer(customer);
		newOrder.setName(customer.getName());
		newOrder.setAddress(customer.getAddress());
		//newOrder.setDistrict(district.getName()); 
		newOrder.setDistrict(null); 
		newOrder.setPhoneNumber(customer.getPhoneNumber());
		newOrder.setOrderTime(new Date());
		newOrder.setTotalCost((double) estimatedTotal);
		newOrder.setPaymentMethod(null);
		newOrder.setOrderStatus(OrderStatus.NEW);
//		Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
//		Double subtotal = 0.0;  
//		for (CartItem cartItem : cartItems) {
//			Product productInCart = cartItem.getProduct();
//			OrderDetail orderDetail = new OrderDetail();
//			orderDetail.setOrder(newOrder);
//			orderDetail.setProduct(productInCart);
//			orderDetail.setQuantity(cartItem.getQuantity());
//			orderDetail.setProductCost(productInCart.getPrice() * cartItem.getQuantity());
//			orderDetail.setSubtotalCost(cartItem.getSubtotal());
//			subtotal += orderDetail.getSubtotalCost();
//			orderDetails.add(orderDetail);
//		}
//
		for (CartItem item : cartItems) {
			estimatedTotal += item.getSubtotal();
		}
		newOrder.setTotalCost((double) estimatedTotal);
		return repo.save(newOrder);	}

	public void processOrder(Customer customer, String district, PaymentMethod payment, List<CartItem> cartItems) {
	    Order newOrder = new Order();

	    newOrder.setCustomer(customer);
	    newOrder.setName(customer.getName());
	    newOrder.setPhoneNumber(customer.getPhoneNumber());
	    newOrder.setAddress(customer.getAddress());
	    newOrder.setDistrict(district);	   	    
	    newOrder.setTotalCost((double) 120000);
	    newOrder.setOrderTime(new Date());
	    newOrder.setOrderStatus(OrderStatus.NEW);
	    newOrder.setPaymentMethod(payment);
	    
	    Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
		Double subtotal = 0.0;  	 
		float estimatedTotal = 0.0f;

		  
		for (CartItem cartItem : cartItems) {
	        Product productInCart = cartItem.getProduct();
	        OrderDetail orderDetail = new OrderDetail();
	        orderDetail.setOrder(newOrder);
	        orderDetail.setProduct(productInCart);
	        orderDetail.setQuantity(cartItem.getQuantity());
	        orderDetail.setProductCost(productInCart.getPrice());
	        orderDetail.setSubtotalCost(productInCart.getPrice() * cartItem.getQuantity());
	        subtotal += orderDetail.getSubtotalCost();
	        
	        estimatedTotal += cartItem.getSubtotal();
	       
	        orderDetails.add(orderDetail);
	    }
		for (OrderDetail orderDetail : orderDetails) {
		    orderDetail.setTotalSubTotalCost((double) subtotal);
		    Double shippingPrice = districtService.getShippingPriceForDistrict(district);
	        orderDetail.setShippingCost(shippingPrice);
	        orderDetail.setTotalCost(subtotal+shippingPrice);
		}
		
       

	   	repo.save(newOrder);
	}


	
	
	   
	

	


	

	
	
}