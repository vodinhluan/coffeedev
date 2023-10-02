package com.coffeedev.admin.user;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coffeedev.admin.FileUploadUtil;
import com.coffeedev.admin.export.UserPDFExporter;
import com.coffeedev.common.entity.Role;
import com.coffeedev.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

@Controller
public class UserController {
	@Autowired
	private UserService service;
	
	@GetMapping("/users")	
	public String listFirstPage(Model model) {
//		List<User> listUsers = service.listAll();
// 		model.addAttribute("listUsers", listUsers);
		return listByPage(1, model);		
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRole = service.listRoles(); 
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRole", listRole);
		model.addAttribute("pageTitle", "Create New User");
		return "users/user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhoto(fileName);
			User savedUser = service.save(user);

			String uploadDir = "user-photos/" + savedUser.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		} else {
			if (user.getPhoto().isEmpty()) user.setPhoto(null);
			service.save(user);
		}

		redirectAttributes.addFlashAttribute("message", "Lưu thành công User");
		
		return "redirect:/users";
	}
	
	
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id,
			Model model,
			RedirectAttributes redirectAttributes) { // path variable
		try {
			User user = service.get(id);
			List<Role> listRole = service.listRoles(); 
			model.addAttribute("user", user);
			model.addAttribute("listRole", listRole);
			model.addAttribute("pageTitle", "Edit User (ID = "+id+")");
			return "users/user_form";

		} catch(UserNotFoundException ex) {
	 		redirectAttributes.addFlashAttribute("message", ex.getMessage());
	 		return "redirect:/users";
		}
	}	
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id,
			Model model,
			RedirectAttributes redirectAttributes) { // path variable
		try {
			service.delete(id);
	 		redirectAttributes.addFlashAttribute("message", 
	 				"Tài khoản với ID " + id + " đã xóa thành công");
		} catch(UserNotFoundException ex) {
	 		redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
 		return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "kích hoạt" : "vô hiệu hóa";
		String message = "Tài khoản với ID " + id + " đã " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/users";
	}

	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
		Page<User> page = service.listByPage(pageNum);
		List<User> listUsers = page.getContent();

		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);

		return "users/users";
	}
	
	@GetMapping("/users/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws IOException {
		List<User> listUsers=service.listAll();
		UserPDFExporter exporter= new UserPDFExporter();
		exporter.export(listUsers, response);
	}
	
}
