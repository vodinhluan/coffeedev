package com.coffeedev.admin.order;

public class OrderNotFoundException extends Exception {
	public OrderNotFoundException(String message) {
		super(message);
	}
}
