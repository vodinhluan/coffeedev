package com.coffeedev.admin.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coffeedev.admin.order.OrderService;
import com.coffeedev.common.dto.OrderStatisticsDTO;
import com.coffeedev.common.dto.OrderSummaryDTO;

@RestController
@RequestMapping("/api/dashboard")
public class OrderStatisticsRestController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/summary")
    public ResponseEntity<OrderSummaryDTO> getOrderSummary() {
        return ResponseEntity.ok(orderService.getOrderSummary());
    }

    @GetMapping("/orders/statistics")
    public ResponseEntity<List<OrderStatisticsDTO>> getOrderStatisticsByDate() {
        return ResponseEntity.ok(orderService.getOrderStatisticsByDate());
    }
}
