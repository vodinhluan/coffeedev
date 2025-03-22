package com.coffeedev.admin.ingredient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.coffeedev.common.entity.InventoryLog;

@Repository
public interface InventoryLogRepository extends CrudRepository<InventoryLog, Integer> {
    // Tìm kiếm theo ingredient hoặc theo khoảng thời gian nếu cần
}
