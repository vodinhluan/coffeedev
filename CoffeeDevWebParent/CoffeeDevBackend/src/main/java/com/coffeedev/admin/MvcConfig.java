package com.coffeedev.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Users Images
		String dirName = "user-photos";
		Path userPhotosDir = Paths.get(dirName);
		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:" + userPhotosPath + "/");

		// Categories Images
		String categoryImagesDirName = "../category-images";
		Path categoryImagesDir = Paths.get(categoryImagesDirName);
		String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/category-images/**").addResourceLocations("file:" + categoryImagesPath + "/");

		// Products Images
		String productImagesDirName = "../product-images";
		Path productImagesDir = Paths.get(productImagesDirName);
		String productImagesPath = productImagesDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/product-images/**").addResourceLocations("file:" + productImagesPath + "/");

	}

}