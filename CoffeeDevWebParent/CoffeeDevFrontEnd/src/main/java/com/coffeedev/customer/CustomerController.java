package com.coffeedev.customer;

import java.io.UnsupportedEncodingException;

import org.aspectj.apache.bcel.classfile.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.security.CustomerUserDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import com.coffeedev.setting.Utilities;
@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/register.html")
	public String viewLogin(Model model) {
		model.addAttribute("customer", new Customer());
		return "register/register";
	}

	@PostMapping("/create_customer")
	public String createCustomer(Customer customer, Model model, HttpServletRequest request) 
			throws UnsupportedEncodingException, MessagingException {
		customerService.registerCustomer(customer);
		String siteURL = Utilities.getSiteURL(request);
		customerService.sendVerificationEmail(customer, siteURL);
		return "register/register_success";
	}

//	private void sendVerificationEmail(HttpServletRequest request, Customer customer)
//			throws UnsupportedEncodingException, MessagingException {
//		EmailSettingBag emailSettings = settingService.getEmailSettings();
//		JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
//
//		String toAddress = customer.getEmail();
//		String subject = emailSettings.getCustomerVerifySubject();
//		String content = emailSettings.getCustomerVerifyContent();
//
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//
//		helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
//		helper.setTo(toAddress);
//		helper.setSubject(subject);
//
//		content = content.replace("[[name]]", customer.getName());
//
//		String verifyURL = Utility.getSiteURL(request) + "/verify?code=" + customer.getVerificationCode();
//
//		content = content.replace("[[URL]]", verifyURL);
//
//		helper.setText(content, true);
//
//		mailSender.send(message);
//
//		System.out.println("to Address: " + toAddress);
//		System.out.println("Verify URL: " + verifyURL);
//	}
//
	@GetMapping("/verify")
	public String verifyAccount(String code, Model model) {
		boolean verified = customerService.verify(code);

		return "register/" + (verified ? "verify_success" : "verify_fail");
	}
//
//
//
//	private CustomerUserDetails getCustomerUserDetailsObject(Object principal) {
//		CustomerUserDetails userDetails = null;
//		if (principal instanceof UsernamePasswordAuthenticationToken) {
//			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
//			userDetails = (CustomerUserDetails) token.getPrincipal();
//		} else if (principal instanceof RememberMeAuthenticationToken) {
//			RememberMeAuthenticationToken token = (RememberMeAuthenticationToken) principal;
//			userDetails = (CustomerUserDetails) token.getPrincipal();
//		}
//
//		return userDetails;
//	}
}
