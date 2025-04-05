package com.coffeedev.common.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatisticsDTO {
    private LocalDate orderDate;
    private long orderCount;
}

