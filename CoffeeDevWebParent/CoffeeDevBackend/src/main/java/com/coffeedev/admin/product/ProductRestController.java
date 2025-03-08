package com.coffeedev.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coffeedev.common.dto.ProductDTO;
import com.coffeedev.common.entity.Category;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    // ✅ Get All Products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.listAll();
        return ResponseEntity.ok(products);
    }

    // ✅ Get All Categories
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = productService.listCategories();
        return ResponseEntity.ok(categories);
    }

    // ✅ Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        try {
            ProductDTO product = productService.get(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // ✅ Create New Product
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProduct = productService.save(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // ✅ Update Product
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        try {
            productDTO.setId(id); // Ensure ID is set correctly
            ProductDTO updatedProduct = productService.save(productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật sản phẩm thất bại.");
        }
    }

    // ✅ Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok("Sản phẩm đã được xóa.");
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // ✅ Update Product Enabled Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateEnabledStatus(@PathVariable Integer id, @RequestParam boolean enabled) {
        try {
            productService.updateProductEnabledStatus(id, enabled);
            return ResponseEntity.ok("Trạng thái sản phẩm đã được cập nhật.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể cập nhật trạng thái sản phẩm.");
        }
    }
}
