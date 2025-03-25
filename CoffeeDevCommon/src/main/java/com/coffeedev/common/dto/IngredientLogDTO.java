package com.coffeedev.common.dto;

import com.coffeedev.common.entity.IngredientLog;
import com.coffeedev.common.entity.IngredientLogType;

public class IngredientLogDTO {
    private Integer id;
    private String ingredientName; 
    private IngredientLogType type;
    private Double quantity;
    private String createdBy;
    private String createdAt;

    public IngredientLogDTO(IngredientLog log) {
        this.id = log.getId();
        this.ingredientName = log.getIngredient().getName();
        this.type = log.getType();
        this.quantity = log.getQuantity();
        this.createdBy = log.getCreatedBy();
        this.createdAt = log.getCreatedAt().toString();
    }

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public IngredientLogType getType() {
        return type;
    }

    public void setType(IngredientLogType type) {
        this.type = type;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}