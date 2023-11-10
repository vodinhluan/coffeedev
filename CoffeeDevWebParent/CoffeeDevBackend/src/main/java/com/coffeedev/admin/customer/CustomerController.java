package com.coffeedev.admin.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.coffeedev.admin.paging.PagingAndSortingParam;
import java.util.List;

import com.coffeedev.admin.paging.PagingAndSortingHelper;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.exception.CustomerNotFoundException;

@Controller
public class CustomerController {

	@Autowired private CustomerService service;

	@GetMapping("/customers")
	public String listFirstPage(Model model) {
		return listByPage(model, 1, "name", "asc", null);
	}

	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(Model model, 
						@PathVariable(name = "pageNum") int pageNum,
						@Param("sortField") String sortField,
						@Param("sortDir") String sortDir,
						@Param("keyword") String keyword
			) {

		Page<Customer> page = service.listByPage(pageNum, sortField, sortDir, keyword);
		List<Customer> listCustomers = page.getContent();

		long startCount = (pageNum - 1) * CustomerService.CUSTOMERS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);

		long endCount = startCount + CustomerService.CUSTOMERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("listCustomers", listCustomers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("endCount", endCount);

		return "customers/customers";
	}

	@GetMapping("/customers/{id}/enabled/{status}")
	public String updateCustomerEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateCustomerEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The Customer ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/customers";
	}	

	@GetMapping("/customers/detail/{id}")
	public String viewCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			Customer customer = service.get(id);
			model.addAttribute("customer", customer);

			return "customers/customer_form";
		} catch (CustomerNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/customers";			
		}
	}


	@PostMapping("/customers/save")
	public String saveCustomer(Customer customer, Model model, RedirectAttributes ra) {
		service.save(customer);
		ra.addFlashAttribute("message", "The customer ID " + customer.getId() + " has been updated successfully.");
		return "redirect:/customers";
	}

	@GetMapping("/customers/delete/{id}")
	public String deleteCustomer(@PathVariable Integer id, RedirectAttributes ra) {
		try {
			service.delete(id);			
			ra.addFlashAttribute("message", "The customer ID " + id + " has been deleted successfully.");

		} catch (CustomerNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/customers";
	}

}