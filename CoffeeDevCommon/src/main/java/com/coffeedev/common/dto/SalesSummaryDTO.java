package com.coffeedev.common.dto;

public class SalesSummaryDTO {
    private String timePeriod; // orderDate hoặc weekNumber
    private long totalOrders;
    private double totalRevenue;

    public SalesSummaryDTO(String timePeriod, long totalOrders, double totalRevenue) {
        this.timePeriod = timePeriod;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
    }

    public String getTimePeriod() { return timePeriod; }
    public void setTimePeriod(String timePeriod) { this.timePeriod = timePeriod; }
    public long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(long totalOrders) { this.totalOrders = totalOrders; }
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
}

