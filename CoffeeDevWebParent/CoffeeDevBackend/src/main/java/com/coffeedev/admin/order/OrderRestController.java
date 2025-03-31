package com.coffeedev.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coffeedev.common.dto.OrderDTO;
import com.coffeedev.common.entity.Order;
import com.coffeedev.common.entity.OrderDetail;
import com.coffeedev.common.entity.OrderStatus;
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

            // Không cho cập nhật nếu đơn hàng đã hoàn tất
            if (existingOrder.getOrderStatus() == OrderStatus.DELIVERED ||
                    existingOrder.getOrderStatus() == OrderStatus.CANCELLED) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Không thể cập nhật đơn hàng ở trạng thái " + existingOrder.getOrderStatus());
            }

            // Cập nhật thông tin cơ bản của đơn hàng
            existingOrder.setPaymentMethod(orderDetails.getPaymentMethod());
            existingOrder.setOrderStatus(orderDetails.getOrderStatus());
            existingOrder.setName(orderDetails.getName());
            existingOrder.setPhoneNumber(orderDetails.getPhoneNumber());
            existingOrder.setAddress(orderDetails.getAddress());
            existingOrder.setDistrict(orderDetails.getDistrict());
            existingOrder.setTotalCost(orderDetails.getTotalCost());

            // ✅ Cập nhật danh sách OrderDetails
            for (OrderDetail updatedDetail : orderDetails.getOrderDetails()) {
                for (OrderDetail existingDetail : existingOrder.getOrderDetails()) {
                    if (existingDetail.getId().equals(updatedDetail.getId())) {
                        existingDetail.setQuantity(updatedDetail.getQuantity());
                        existingDetail.setProductCost(updatedDetail.getProductCost());
                        existingDetail.setShippingCost(updatedDetail.getShippingCost());
                        existingDetail.setSubtotalCost(updatedDetail.getSubtotalCost());
                        existingDetail.setTotalCost(updatedDetail.getTotalCost());

                        // ✅ Cập nhật tên sản phẩm (productName)
                        existingDetail.setProductName(updatedDetail.getProductName());
                    }
                }
            }

            service.save(existingOrder);

            // ✅ CHUYỂN ORDER THÀNH DTO TRƯỚC KHI TRẢ VỀ ĐỂ TRÁNH VÒNG LẶP
            OrderDTO orderDTO = OrderMapper.toDTO(existingOrder);
            return ResponseEntity.ok(orderDTO);

        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
