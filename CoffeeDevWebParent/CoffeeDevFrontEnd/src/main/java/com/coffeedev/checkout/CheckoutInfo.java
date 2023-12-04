package com.coffeedev.checkout;

import java.util.ArrayList;
import java.util.List;

import com.coffeedev.common.entity.CartItem;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.entity.District;
import com.coffeedev.common.entity.PaymentMethod;

public class CheckoutInfo {
    // Thông tin khách hàng
    private Customer customer;

    // Thông tin về đơn hàng
    private District district;
    private List<CartItem> cartItems;
    private PaymentMethod paymentMethod;

    // Các thông tin chi phí và tổng cộng
    private Double productCost;
    private Double shippingCostTotal;
    private Double paymentTotal;

    // Các phương thức getter và setter
    public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Double getProductCost() {
		return productCost;
	}

	public void setProductCost(Double productCost) {
		this.productCost = productCost;
	}

	public Double getShippingCostTotal() {
		return shippingCostTotal;
	}

	public void setShippingCostTotal(Double shippingCostTotal) {
		this.shippingCostTotal = shippingCostTotal;
	}

	public Double getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(Double paymentTotal) {
		this.paymentTotal = paymentTotal;
	}
    
    // Nếu cần, có thể thêm các phương thức hỗ trợ khác
    // Ví dụ: phương thức để thêm một sản phẩm vào giỏ hàng
    public void addCartItem(CartItem cartItem) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(cartItem);
    }

	
}
