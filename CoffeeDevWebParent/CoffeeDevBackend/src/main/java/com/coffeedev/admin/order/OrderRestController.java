package com.coffeedev.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coffeedev.common.dto.OrderDTO;
import com.coffeedev.common.entity.Order;
import com.coffeedev.common.mapper.OrderMapper;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<?> listByPage(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String keyword) {
    
        List<OrderDTO> orders = service.listByPage(pageNum, sortField, sortDir, keyword);
    
        return ResponseEntity.ok(orders);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") Integer id) {
        try {
            Order order = service.get(id);
            System.out.println("Order: " + order.getId());
            System.out.println(
                    "OrderDetails: " + (order.getOrderDetails() == null ? "null" : order.getOrderDetails().size()));
            OrderDTO orderDTO = OrderMapper.toDTO(order);
            return ResponseEntity.ok(orderDTO);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Order ID " + id + " has been deleted successfully.");
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = service.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Integer id, @RequestBody Order orderDetails) {
        try {
            Order existingOrder = service.get(id);
            existingOrder.setPaymentMethod(orderDetails.getPaymentMethod());
            existingOrder.setOrderStatus(orderDetails.getOrderStatus());
            service.save(existingOrder);
            return ResponseEntity.ok(existingOrder);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
