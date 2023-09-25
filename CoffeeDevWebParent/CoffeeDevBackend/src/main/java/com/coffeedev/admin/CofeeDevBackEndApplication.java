package com.coffeedev.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.coffeedev.common.entity", "com.coffeedev.admin.user"})
public class CofeeDevBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(CofeeDevBackEndApplication.class, args);
	}

}
