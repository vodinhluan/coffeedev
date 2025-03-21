package com.coffeedev.common.dto;

import java.util.HashSet;
import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CategoryDTO {
    private Integer id;

    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
    
    private String image;
    private boolean enabled;
    private CategoryDTO parent; // Không cần @JsonBackReference nữa
    private Set<CategoryDTO> children = new HashSet<>();
    
    @Override
    public String toString() {
        return this.name;
    }
}
