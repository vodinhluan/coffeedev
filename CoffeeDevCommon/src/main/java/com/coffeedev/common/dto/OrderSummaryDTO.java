package com.coffeedev.common.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderSummaryDTO {
    private long totalOrders;
    private double totalSales;
    private long orderCountCurrentWeek;
    private long orderCountCurrentMonth;
    private double totalSalesCurrentMonth;
    private Map<String, Long> orderStatusCount;
}
