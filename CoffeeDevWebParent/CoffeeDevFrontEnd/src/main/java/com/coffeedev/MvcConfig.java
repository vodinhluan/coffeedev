package com.coffeedev;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	
		String productImagesDirName = "../product-images";
		Path productImagesDir = Paths.get(productImagesDirName);
		String productImagesPath = productImagesDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/product-images/**").addResourceLocations("file:" + productImagesPath + "/");
	}

}