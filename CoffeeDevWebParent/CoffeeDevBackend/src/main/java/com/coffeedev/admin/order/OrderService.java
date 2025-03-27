package com.coffeedev.admin.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.coffeedev.common.dto.OrderDTO;
import com.coffeedev.common.entity.Order;
import com.coffeedev.common.mapper.OrderMapper;

@Service
public class OrderService {

    static final int ORDER_PER_PAGE = 10;

    @Autowired
    private OrderRepository repo;

    public List<OrderDTO> listByPage(Integer pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField).descending(); // Mặc định giảm dần theo ID
        if (sortDir.equals("asc")) {
            sort = Sort.by(sortField).ascending();
        }

        if (pageNum == null || pageNum <= 0) {
            // Nếu không truyền pageNum hoặc pageNum = 0, trả về toàn bộ danh sách
            List<Order> orders = (keyword == null || keyword.isEmpty()) ? (List<Order>) repo.findAll(sort)
                    : repo.findAll(keyword, sort);
            return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
        }

        Pageable pageable = PageRequest.of(pageNum - 1, ORDER_PER_PAGE, sort);
        Page<Order> page = (keyword == null || keyword.isEmpty()) ? repo.findAll(pageable)
                : repo.searchOrders(keyword, pageable);

        return page.getContent().stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    public Order get(Integer id) throws OrderNotFoundException {
        return repo.findByIdWithDetails(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }

    public void delete(Integer id) throws OrderNotFoundException {
        Long countById = repo.countById(id);
        if (countById == 0) {
            throw new OrderNotFoundException("Could not find any orders with ID: " + id);
        }
        repo.deleteById(id);
    }

    public Order save(Order order) {
        return repo.save(order);
    }
}
