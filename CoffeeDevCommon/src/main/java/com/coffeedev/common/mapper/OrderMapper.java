package com.coffeedev.common.mapper;

import java.util.Collections;
import com.coffeedev.common.dto.OrderDTO;
import com.coffeedev.common.dto.OrderDetailDTO;
import com.coffeedev.common.entity.Order;
import com.coffeedev.common.entity.OrderDetail;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setName(order.getName());
        dto.setPhoneNumber(order.getPhoneNumber());
        dto.setAddress(order.getAddress());
        dto.setDistrict(order.getDistrict());
        dto.setOrderTime(order.getOrderTime());
        dto.setTotalCost(order.getTotalCost());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setCustomerId(order.getCustomer().getId());

        // Kiểm tra nếu orderDetails không null thì map vào DTO
        if (order.getOrderDetails() != null) {
            dto.setOrderDetails(order.getOrderDetails().stream()
                    .map(OrderMapper::toOrderDetailDTO) // Gọi đúng mapper
                    .toList());
        } else {
            dto.setOrderDetails(Collections.emptyList());
        }

        return dto;
    }

    // Đảm bảo đây là phương thức trong OrderMapper, không phải OrderDetailDTO
    public static OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(orderDetail.getId());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setProductCost(orderDetail.getProductCost());
        dto.setShippingCost(orderDetail.getShippingCost());
        dto.setSubtotalCost(orderDetail.getSubtotalCost());
        dto.setTotalCost(orderDetail.getTotalCost());
        dto.setProductId(orderDetail.getProduct().getId());
        dto.setProductName(orderDetail.getProduct().getName()); 

        return dto;
    }
}
