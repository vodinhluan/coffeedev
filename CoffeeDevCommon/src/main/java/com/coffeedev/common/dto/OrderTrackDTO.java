package com.coffeedev.common.dto;

import java.util.Date;

import com.coffeedev.common.entity.OrderStatus;

public class OrderTrackDTO {
    private Integer id;
    private String notes;
    private Date updatedTime;
    private OrderStatus status;
    private Integer orderId;

    // Constructors, Getters, and Setters
    

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
     * @return String return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return Date return the updatedTime
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * @param updatedTime the updatedTime to set
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * @return OrderStatus return the status
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * @return Integer return the orderId
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}

