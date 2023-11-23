package com.coffeedev.checkout;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffeedev.common.entity.CartItem;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.entity.District;
import com.coffeedev.common.entity.ShippingRate;
import com.coffeedev.customer.CustomerService;
import com.coffeedev.setting.Utilities;
import com.coffeedev.shoppingcart.ShoppingCartService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CheckoutController {
	@Autowired
	private CheckoutService checkoutService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private ShoppingCartService cartService;
	
	@GetMapping("/checkout")
	public String showCheckoutPage(Model model, HttpServletRequest request) {
	    List<District> districts = checkoutService.getAllDistricts();
	    
		String email = Utilities.getEmailOfAuthenticatedCustomer(request);
		Customer customer = customerService.getCustomerByEmail(email);
		List<CartItem> cartItems = cartService.listCartItems(customer);
		float estimatedTotal = 0.0F;
		for (CartItem item : cartItems) {
			estimatedTotal += item.getSubtotal();
		}

		model.addAttribute("customer", customer);
		model.addAttribute("districts", districts);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("estimatedTotal", estimatedTotal);
	    return "check_out/checkout";
	}
	
	@GetMapping("/checkout/checkout_success")
	public String viewCheckoutSuccess() {
		return "check_out/checkout_success";
	}
	
	
	
	



	
}
