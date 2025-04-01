package com.coffeedev.admin.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.coffeedev.common.dto.OrderDTO;
import com.coffeedev.common.dto.OrderStatisticsDTO;
import com.coffeedev.common.dto.OrderSummaryDTO;
import com.coffeedev.common.entity.Order;
import com.coffeedev.common.entity.OrderStatus;
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

    public OrderSummaryDTO getOrderSummary() {
        long totalOrders = repo.getTotalOrders();
        double totalSales = repo.getTotalSales() != null ? repo.getTotalSales() : 0.0;
        long orderCountCurrentWeek = repo.getOrderCountCurrentWeek();
        long orderCountCurrentMonth = repo.getOrderCountCurrentMonth();
        double totalSalesCurrentMonth = repo.getTotalSalesCurrentMonth() != null ? repo.getTotalSalesCurrentMonth()
                : 0.0;

        // Lấy danh sách số lượng đơn hàng theo trạng thái
        Map<String, Long> orderStatusCount = new HashMap<>();
        List<Object[]> statusResults = repo.getOrderCountByStatus();
        for (Object[] row : statusResults) {
            orderStatusCount.put(((OrderStatus) row[0]).name(), ((Number) row[1]).longValue());
        }

        return new OrderSummaryDTO(totalOrders, totalSales, orderCountCurrentWeek, orderCountCurrentMonth,
                totalSalesCurrentMonth, orderStatusCount);
    }

    public List<OrderStatisticsDTO> getOrderStatisticsByDate() {
        List<Object[]> results = repo.getOrderCountByDate();
        return results.stream()
                .map(row -> new OrderStatisticsDTO(((java.sql.Date) row[0]).toLocalDate(),
                        ((Number) row[1]).longValue()))
                .collect(Collectors.toList());
    }
}
