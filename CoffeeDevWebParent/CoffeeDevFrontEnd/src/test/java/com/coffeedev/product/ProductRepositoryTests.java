package com.coffeedev.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.coffeedev.common.entity.Category;
import com.coffeedev.common.entity.Product;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repo;

    @Test
    public void testListProduct() {
        List<Product> products = (List<Product>) repo.findAll();

        for (Product product : products) {
            System.out.println("Product ID: " + product.getId());
            System.out.println("Product Name: " + product.getName());
            System.out.println("Product Image: " + product.getImage());
            System.out.println("-------------------------------");
        }
    }
    
    @Test
    public void testListProductByCategory() {
        List<Product> productsByCategory = repo.findAllByCategoryName("DRINKS");

        for (Product product : productsByCategory) {
            System.out.println("Product ID: " + product.getId());
            System.out.println("Product Name: " + product.getName());
            System.out.println("Product Image: " + product.getImage());
            System.out.println("-------------------------------");
        }
    }
    
   
}

