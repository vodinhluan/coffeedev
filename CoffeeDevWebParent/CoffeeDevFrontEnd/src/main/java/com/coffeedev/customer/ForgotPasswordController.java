package com.coffeedev.customer;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.exception.CustomerNotFoundException;
import com.coffeedev.setting.Utilities;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ForgotPasswordController {
	@Autowired private CustomerService customerService;
	@Autowired JavaMailSender mailSender;


	@GetMapping("/forgot_password")
	public String showRequestForm() {
		return "customer/forgot_password_form";
	}

	@PostMapping("/forgot_password")
	public String processRequestForm(Customer customer, HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		try {
			String token = customerService.updateResetPasswordToken(email);
			String link = Utilities.getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(customer, link, email);

			model.addAttribute("message", "We have sent a reset password link to your email."
					+ " Please check.");
		} catch (CustomerNotFoundException e) {
			model.addAttribute("error", e.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Could not send email");
		}

		return "customer/forgot_password_form";
	}

	private void sendEmail(Customer customer, String link, String email) 
			throws UnsupportedEncodingException, MessagingException {
		String subject = "Reset Your Password";
		String senderName ="CoffeeDev";
		String content = "<p>Hello,</p>"
				+ "<p>You have requested to reset your password.</p>"
				+ "Click the link below to change your password:</p>"
				+ "<p><a href=\"" + link + "\">Change my password</a></p>"
				+ "<br>"
				+ "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";
		String verifyURL = link + "/verify?code=" + customer.getVerificationCode();
		MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom("marcoluan2002@gmail.com", senderName);
	    helper.setTo(customer.getEmail());
	    helper.setSubject(subject);
	        
	    helper.setText(content, true);
	    mailSender.send(message);
	}

	@GetMapping("/reset_password")
	public String showResetForm(@Param("token") String token, Model model) {
		Customer customer = customerService.getByResetPasswordToken(token);
		if (customer != null) {
			model.addAttribute("token", token);
		} else {
			model.addAttribute("pageTitle", "Invalid Token");
			model.addAttribute("message", "Invalid Token");
			return "message";
		}

		return "customer/reset_password_form";
	}

	@PostMapping("/reset_password")
	public String processResetForm(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");

		try {
			customerService.updatePassword(token, password);

			model.addAttribute("pageTitle", "Reset Your Password");
			model.addAttribute("title", "Reset Your Password");
			model.addAttribute("message", "You have successfully changed your password.");

		} catch (CustomerNotFoundException e) {
			model.addAttribute("pageTitle", "Invalid Token");
			model.addAttribute("message", e.getMessage());
		}	

		return "message";		
	}
}
