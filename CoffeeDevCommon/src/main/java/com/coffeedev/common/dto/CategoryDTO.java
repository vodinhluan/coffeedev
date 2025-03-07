package com.coffeedev.common.dto;

import java.util.HashSet;
import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
public class CategoryDTO {
    private Integer id;
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
    private String image;
    private boolean enabled;

    @JsonBackReference // Đánh dấu parent để tránh tuần hoàn
    private CategoryDTO parent;

    @JsonManagedReference // Đánh dấu children để cho phép tuần hoàn
    private Set<CategoryDTO> children = new HashSet<>();

    @Override
    public String toString() {
        return this.name;
    }
}
