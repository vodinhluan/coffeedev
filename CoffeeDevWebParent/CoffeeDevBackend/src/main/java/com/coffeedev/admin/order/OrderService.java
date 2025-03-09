package com.coffeedev.admin.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.coffeedev.common.entity.Order;

@Service
public class OrderService {

    static final int ORDER_PER_PAGE = 10;

    @Autowired
    private OrderRepository repo;

    public Page<Order> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, ORDER_PER_PAGE, sort);

        return (keyword == null || keyword.isEmpty()) ? repo.findAll(pageable) : repo.findAll(keyword, pageable);
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
