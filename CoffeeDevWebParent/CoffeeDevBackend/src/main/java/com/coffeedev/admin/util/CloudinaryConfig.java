package com.coffeedev.admin.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CloudinaryConfig {

    private final Dotenv dotenv = Dotenv.load();

    private final String cloudName = dotenv.get("CLOUDINARY_CLOUD_NAME");
    private final String apiKey = dotenv.get("CLOUDINARY_API_KEY");
    private final String apiSecret = dotenv.get("CLOUDINARY_API_SECRET");

    public CloudinaryConfig() {
        System.out.println("Cloud Name: " + cloudName);
        System.out.println("API Key: " + apiKey);
        System.out.println("API Secret: " + apiSecret);
    }

    @Bean
    public Cloudinary cloudinary() {
        Map config = ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        );
        return new Cloudinary(config);
    }
}
