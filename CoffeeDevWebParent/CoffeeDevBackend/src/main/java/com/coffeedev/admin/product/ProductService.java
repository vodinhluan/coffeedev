package com.coffeedev.admin.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coffeedev.admin.category.CategoryRepository;
import com.coffeedev.common.entity.Category;
import com.coffeedev.common.entity.Product;
import com.coffeedev.common.dto.ProductDTO;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository cateRepo;

    // ✅ Convert Product to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getAlias(),
            product.getDescription(),
            product.getPrice(),
            product.getImage(),
            product.isEnabled(),
            product.getCategory().getId()
        );
    }

    // ✅ Get All Products as DTOs
    public List<ProductDTO> listAll() {
        return productRepo.findAll()
                          .stream()
                          .map(this::convertToDTO)
                          .collect(Collectors.toList());
    }

    // ✅ Get All Categories
    public List<Category> listCategories() {
        return cateRepo.findAll();
    }

    // ✅ Save or Update Product
    public ProductDTO save(ProductDTO productDTO) {
        Product product = productRepo.findById(productDTO.getId())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
    
        // Cập nhật các trường từ productDTO
        product.setName(productDTO.getName());
        product.setAlias(productDTO.getAlias());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setEnabled(productDTO.isEnabled());
        product.setCategory(new Category(productDTO.getCategoryId()));
    
        // Chỉ cập nhật image nếu có giá trị mới
        if (productDTO.getImage() != null) {
            product.setImage(productDTO.getImage());
        }
    
        Product savedProduct = productRepo.save(product);
        return convertToDTO(savedProduct);
    }
    

    // ✅ Get Product by ID
    public ProductDTO get(Integer id) throws ProductNotFoundException {
        return productRepo.findById(id)
                          .map(this::convertToDTO)
                          .orElseThrow(() -> 
                              new ProductNotFoundException("Không thể tìm thấy sản phẩm nào với ID: " + id));
    }

    // ✅ Delete Product by ID
    public void delete(Integer id) throws ProductNotFoundException {
        if (!productRepo.existsById(id)) {
            throw new ProductNotFoundException("Không thể tìm thấy sản phẩm nào với ID: " + id);
        }
        productRepo.deleteById(id);
    }

    // ✅ Update Enabled Status
    @Transactional
    public void updateProductEnabledStatus(Integer id, boolean enabled) {
        productRepo.updateEnabledStatus(id, enabled);  
    }
}
