package com.coffeedev.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan({"com.coffeedev.common.entity", "com.coffeedev.admin.user"})
public class CoffeeDevBackEndApplication {
	public static void main(String[] args) {
		SpringApplication.run(CoffeeDevBackEndApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
