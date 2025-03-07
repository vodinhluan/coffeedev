package com.coffeedev.common.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class OrderDTO {
    private Integer id;
    private Date orderTime;
    private BigDecimal total;
    private String status;
    private Integer customerId;
    private List<OrderDetailDTO> orderDetails;
}

