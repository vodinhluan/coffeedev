package com.coffeedev.checkout;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.entity.District;
import com.coffeedev.customer.CustomerService;
import com.coffeedev.setting.Utilities;
import com.coffeedev.shoppingcart.ShoppingCartService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CheckoutController {
	@Autowired private CheckoutService checkoutService;
	@Autowired private CustomerService customerService;
	
	@GetMapping("/checkout")
	public String showCheckoutPage(Model model, HttpServletRequest request) {
	    List<District> districts = checkoutService.getAllDistricts();
		String email = Utilities.getEmailOfAuthenticatedCustomer(request);
		Customer customer = customerService.getCustomerByEmail(email);
		model.addAttribute("customer", customer);
	    model.addAttribute("districts", districts);
	    return "check_out/checkout";
	}



	
}
