package com.coffeedev.common.dto;

import java.util.Date;
import java.util.List;
import com.coffeedev.common.entity.Order;
import com.coffeedev.common.entity.OrderStatus;
import com.coffeedev.common.entity.PaymentMethod;

public class OrderDTO {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String address;
    private String district;
    private Date orderTime;
    private Double totalCost;
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;
    private Integer customerId;
    private List<OrderDetailDTO> orderDetails;

    // No-argument constructor (needed for frameworks)
    public OrderDTO() {
    }

    // Constructor that converts an Order entity to OrderDTO
    public OrderDTO(Order order) {
        this.id = order.getId();
        this.name = order.getName();
        this.phoneNumber = order.getPhoneNumber();
        this.address = order.getAddress();
        this.district = order.getDistrict();
        this.orderTime = order.getOrderTime(); // Ensure orderTime in Order is of type Date
        this.totalCost = order.getTotalCost();
        this.paymentMethod = order.getPaymentMethod();
        this.orderStatus = order.getOrderStatus();
        this.orderDetails = (order.getOrderDetails() != null)
                ? order.getOrderDetails().stream().map(OrderDetailDTO::new).toList()
                : List.of();
    }
    // Generate Constructors, Getters, and Setters
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }
    
    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
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
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return String return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return String return the district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district the district to set
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return Date return the orderTime
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime the orderTime to set
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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

    /**
     * @return PaymentMethod return the paymentMethod
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return OrderStatus return the orderStatus
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
