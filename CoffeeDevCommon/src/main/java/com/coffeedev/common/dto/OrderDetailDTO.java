package com.coffeedev.common.dto;

import com.coffeedev.common.entity.OrderDetail;

public class OrderDetailDTO {
    private Integer id;
    private int quantity;
    private Double productCost;
    private Double shippingCost;
    private Double subtotalCost;
    private Double totalCost;
    private Integer productId; 
    private String productName; 

    // Constructors, Getters, and Setters

    public OrderDetailDTO() {}

    public OrderDetailDTO(OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        this.quantity = orderDetail.getQuantity();
        this.productCost = orderDetail.getProductCost();
        this.productName = orderDetail.getProduct().getName();
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    

    /**
     * @return Integer return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return int return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return Double return the productCost
     */
    public Double getProductCost() {
        return productCost;
    }

    /**
     * @param productCost the productCost to set
     */
    public void setProductCost(Double productCost) {
        this.productCost = productCost;
    }

    /**
     * @return Double return the shippingCost
     */
    public Double getShippingCost() {
        return shippingCost;
    }

    /**
     * @param shippingCost the shippingCost to set
     */
    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    /**
     * @return Double return the subtotalCost
     */
    public Double getSubtotalCost() {
        return subtotalCost;
    }

    /**
     * @param subtotalCost the subtotalCost to set
     */
    public void setSubtotalCost(Double subtotalCost) {
        this.subtotalCost = subtotalCost;
    }

    /**
     * @return Double return the totalCost
     */
    public Double getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost the totalCost to set
     */
    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

}
