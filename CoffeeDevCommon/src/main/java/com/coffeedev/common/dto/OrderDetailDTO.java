package com.coffeedev.common.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderDetailDTO {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}