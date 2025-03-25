package com.coffeedev.admin.ingredient_log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coffeedev.common.entity.IngredientLog;

import java.util.Date;
import java.util.List;

@Repository
public interface IngredientLogRepository extends JpaRepository<IngredientLog, Integer> {
    List<IngredientLog> findByCreatedAtBetween(Date start, Date end);
}

