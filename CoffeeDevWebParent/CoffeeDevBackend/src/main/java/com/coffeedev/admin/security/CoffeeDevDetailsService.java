package com.coffeedev.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coffeedev.admin.user.UserRepository;
import com.coffeedev.common.entity.User;

@Service
public class CoffeeDevDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        System.out.println("DEBUG: Loaded user email: " + user.getEmail());
        System.out.println("DEBUG: Loaded user password: " + user.getPassword()); 

        return new CoffeeDevUserDetails(user);
    }
}

