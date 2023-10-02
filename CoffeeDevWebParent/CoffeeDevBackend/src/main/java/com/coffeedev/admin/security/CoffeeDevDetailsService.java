package com.coffeedev.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.coffeedev.admin.user.UserRepository;
import com.coffeedev.common.entity.User;

public class CoffeeDevDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new CoffeeDevUserDetails(user);
		}
		throw new UsernameNotFoundException("Không tìm thấy user "+email);

	}

}
