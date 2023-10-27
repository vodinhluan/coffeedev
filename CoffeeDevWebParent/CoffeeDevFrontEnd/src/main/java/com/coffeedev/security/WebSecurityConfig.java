package com.coffeedev.security;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.coffeedev.oauth.CustomerOAuth2UserService;
import com.coffeedev.oauth.OAuth2LoginSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	@Autowired 
    @Lazy
	private CustomerOAuth2UserService oAuth2UserService;
	
	@Autowired
    @Lazy
	private OAuth2LoginSuccessHandler oauth2LoginHandler;
	
	@Autowired
    @Lazy
	private DatabaseLoginSuccessHandler databaseLoginHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
	    .authorizeRequests()
	        .requestMatchers("/customer").authenticated()
	    .and()
	    .formLogin()
	    	.loginPage("/login")
	    	.usernameParameter("email")
			.successHandler(databaseLoginHandler)
	    	.permitAll()
	    .and()
	    .oauth2Login()
			.loginPage("/login")
			.userInfoEndpoint()
			.userService(oAuth2UserService)
			.and()
			.successHandler(oauth2LoginHandler)
		.and()
	    .logout()
	        .permitAll()
		.and()
		.rememberMe()
			.key("1234567890_aBcDeFgHiJkLmNoPqRsTuVwXyZ")
			.tokenValiditySeconds(14 * 24 * 60 * 60)
		;	
	    return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}	

	
	
}