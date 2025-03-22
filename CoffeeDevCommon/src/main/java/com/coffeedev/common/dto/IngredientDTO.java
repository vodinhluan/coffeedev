package com.coffeedev.common.dto;

public class IngredientDTO {
    private Integer id;
    private String name;
    private String unit;
    private Double quantity;
    private Double minQuantity;

    // Có thể bỏ qua createdAt, updatedAt nếu không cần hiển thị

    public IngredientDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Double minQuantity) {
        this.minQuantity = minQuantity;
    }
}
