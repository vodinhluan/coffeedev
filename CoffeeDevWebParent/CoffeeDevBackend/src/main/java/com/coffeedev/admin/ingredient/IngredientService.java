package com.coffeedev.admin.ingredient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coffeedev.common.entity.Ingredient;
import com.coffeedev.common.entity.IngredientLog;
import com.coffeedev.common.entity.IngredientLogType;

@Service
@Transactional
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepo;
    
    @Autowired
    private IngredientLogRepository IngredientLogRepo;

    public List<Ingredient> listAll() {
        return (List<Ingredient>) ingredientRepo.findAll();
    }

    public Ingredient get(Integer id) {
        Optional<Ingredient> ingredient = ingredientRepo.findById(id);
        if (!ingredient.isPresent()) {
            throw new NoSuchElementException("Không tìm thấy nguyên liệu với id: " + id);
        }
        return ingredient.get();
    }
    
    
    public Ingredient save(Ingredient ingredient) {
        return ingredientRepo.save(ingredient);
    }

    /**
     * Thực hiện nhập kho:
     * - Cập nhật số lượng nguyên liệu
     * - Lưu log nhập kho
     */
    public Ingredient importIngredient(Integer ingredientId, Double importQty, String createdBy) {
        Ingredient ingredient = get(ingredientId);
        ingredient.setQuantity(ingredient.getQuantity() + importQty);
        Ingredient savedIngredient = ingredientRepo.save(ingredient);
        
        IngredientLog log = new IngredientLog();
        log.setIngredient(savedIngredient);
        log.setType(IngredientLogType.IMPORT);
        log.setQuantity(importQty);
        log.setCreatedBy(createdBy);
        IngredientLogRepo.save(log);
        
        return savedIngredient;
    }

    /**
     * Thực hiện xuất kho:
     * - Trừ số lượng nguyên liệu
     * - Lưu log xuất kho
     */
    public Ingredient exportIngredient(Integer ingredientId, Double exportQty, String createdBy) {
        Ingredient ingredient = get(ingredientId);
        if (ingredient.getQuantity() < exportQty) {
            throw new RuntimeException("Số lượng nguyên liệu không đủ để xuất kho.");
        }
        ingredient.setQuantity(ingredient.getQuantity() - exportQty);
        Ingredient savedIngredient = ingredientRepo.save(ingredient);
        
        IngredientLog log = new IngredientLog();
        log.setIngredient(savedIngredient);
        log.setType(IngredientLogType.EXPORT);
        log.setQuantity(exportQty);
        log.setCreatedBy(createdBy);
        IngredientLogRepo.save(log);
        
        return savedIngredient;
    }

    public void delete(Integer id) {
        ingredientRepo.deleteById(id);
    }
}
