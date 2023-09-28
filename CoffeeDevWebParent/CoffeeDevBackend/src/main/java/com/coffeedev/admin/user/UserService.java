package com.coffeedev.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.coffeedev.common.entity.Role;
import com.coffeedev.common.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class UserService {
	public static final int USERS_PER_PAGE = 4;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	public List<User>listAll() {
		return (List<User>) userRepo.findAll();
	}
	
	public Page<User> listByPage(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);
		return userRepo.findAll(pageable);
	}

	public List<Role>listRoles() {
		return (List<Role>) roleRepo.findAll();
	}


	public User save(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail =  userRepo.getUserByEmail(email); // lấy user trong db
		
		if (userByEmail == null) return true; // nếu user không có trong db -> true 
		// nếu có user trong db -> chạy phía dưới
		boolean isCreatingNew = (id == null); 
		// xét user đó :
		// không có id trong db (id == null) -> true : tạo mới user
		// có id -> false : edit
		
		if (isCreatingNew) {
			if (userByEmail != null) return false;  //có đang tạo mới ? xét trong db, nếu != rỗng -> false
		//	else return true;
		} else {
			if(!userByEmail.getId().equals(id)) { // id trong db != id url
				return false;
			}
		}
		return true;		
	}
	
	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Không tìm thấy user nào với id: "+ id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById =  userRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Không tìm thấy user nào với id: "+ id);
		}
		
		userRepo.deleteById(id);
	}
	
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}
	
	
	
	
}
