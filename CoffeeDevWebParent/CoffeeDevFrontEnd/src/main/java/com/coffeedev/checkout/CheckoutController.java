package com.coffeedev.checkout;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coffeedev.common.entity.CartItem;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.entity.District;
import com.coffeedev.common.entity.Order;
import com.coffeedev.common.entity.PaymentMethod;
import com.coffeedev.common.entity.ShippingRate;
import com.coffeedev.customer.CustomerService;
import com.coffeedev.order.OrderService;
import com.coffeedev.setting.Utilities;
import com.coffeedev.shoppingcart.ShoppingCartService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {
	@Autowired
	private CheckoutService checkoutService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private ShoppingCartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private DistrictService districtService;
	
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
	
	
	@PostMapping("/checkout/submit")
	public String placeOrder(@ModelAttribute("customer") Customer customer,
			@RequestParam("quan") String district,
			@RequestParam("paymentMethod") String payment,
	                         HttpServletRequest request,
	                         Model model) {
	    String email = Utilities.getEmailOfAuthenticatedCustomer(request);
	    Customer existingCustomer = customerService.getCustomerByEmail(email);
	    existingCustomer.setName(customer.getName());
	    existingCustomer.setPhoneNumber(customer.getPhoneNumber());
	    existingCustomer.setAddress(customer.getAddress());
	    
	    PaymentMethod selectedPaymentMethod = null;
	    if ("bankTransfer".equals(payment)) {
	        selectedPaymentMethod = PaymentMethod.CHUYENKHOAN;
	    } else if ("payAtDelivery".equals(payment)) {
	        selectedPaymentMethod = PaymentMethod.COD;
	    }
	    
		List<CartItem> cartItems = cartService.listCartItems(existingCustomer);
		
	    orderService.processOrder(existingCustomer, district, selectedPaymentMethod,
	    		cartItems);
		cartService.deleteByCustomer(existingCustomer);

	    return "check_out/checkout_success";
	}



	
	 
	
//	Double districtShipping = districtService.getShippingPriceForDistrict(district);
//	model.addAttribute("shippingPrice", districtShipping);



	
}
