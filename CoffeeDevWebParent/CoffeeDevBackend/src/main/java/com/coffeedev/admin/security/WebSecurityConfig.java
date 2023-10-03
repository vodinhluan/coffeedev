package com.coffeedev.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CoffeeDevDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;		
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeRequests().requestMatchers("/")

		.authenticated()
		.requestMatchers("/users/**").hasAuthority("Admin")
		.requestMatchers("/categories/**").hasAnyAuthority("Admin", "Staff")

		.and()
		.authorizeRequests().requestMatchers("/users")
		.authenticated()

		.and()
		.formLogin()
			.loginPage("/login")
			.usernameParameter("email")
			.permitAll()
 			.and().logout().permitAll()
			.and().rememberMe().key("AbcDefgHijKlmnOpqrs_1234567890")
			.tokenValiditySeconds(7 * 24 * 60 * 60);
		return http.build();
		}

}