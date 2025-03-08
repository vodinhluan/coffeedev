package com.coffeedev.common.dto;

public class ProductDTO {
    private Integer id;
    private String name;
    private String alias;
    private String description;
    private Double price;
    private String image;
    private boolean enabled;
    private Integer categoryId;

    public ProductDTO(Integer id, String name, String alias, String description, Double price, String image, boolean enabled, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.price = price;
        this.image = image;
        this.enabled = enabled;
        this.categoryId = categoryId;
    }

    public ProductDTO() {}

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
