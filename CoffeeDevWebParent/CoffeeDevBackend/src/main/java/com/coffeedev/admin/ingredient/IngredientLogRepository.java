package com.coffeedev.admin.ingredient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.coffeedev.common.entity.IngredientLog;

@Repository
public interface IngredientLogRepository extends CrudRepository<IngredientLog, Integer> {
    // Tìm kiếm theo ingredient hoặc theo khoảng thời gian nếu cần
}
