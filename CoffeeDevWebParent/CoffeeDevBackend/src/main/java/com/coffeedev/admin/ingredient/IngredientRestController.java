package com.coffeedev.admin.ingredient;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.coffeedev.common.dto.IngredientDTO;
import com.coffeedev.common.dto.IngredientLogDTO;
import com.coffeedev.common.entity.Ingredient;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientRestController {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ModelMapper modelMapper;

    // Lấy danh sách nguyên liệu
    @GetMapping
    public ResponseEntity<List<IngredientDTO>> listAllIngredients() {
        List<Ingredient> ingredients = ingredientService.listAll();
        List<IngredientDTO> ingredientDTOs = ingredients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ingredientDTOs);
    }

    // Lấy thông tin chi tiết của 1 nguyên liệu theo id
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredient(@PathVariable Integer id) {
        try {
            Ingredient ingredient = ingredientService.get(id);
            return ResponseEntity.ok(convertToDTO(ingredient));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO) {
        try {
            Ingredient ingredient = modelMapper.map(ingredientDTO, Ingredient.class);
            Ingredient savedIngredient = ingredientService.save(ingredient);
            return ResponseEntity.ok(convertToDTO(savedIngredient));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Nhập kho cho nguyên liệu có id: POST /api/ingredients/import/{id}
    @PostMapping("/import/{id}")
    public ResponseEntity<IngredientDTO> importIngredient(
            @PathVariable Integer id,
            @RequestBody IngredientLogDTO logDTO) { // ✅ Nhận JSON body
        try {
            Ingredient updated = ingredientService.importIngredient(id, logDTO.getQuantity(), logDTO.getCreatedBy());
            return ResponseEntity.ok(convertToDTO(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Xuất kho cho nguyên liệu có id: POST /api/ingredients/export/{id}
    @PostMapping("/export/{id}")
    public ResponseEntity<IngredientDTO> exportIngredient(
            @PathVariable Integer id,
            @RequestBody IngredientLogDTO logDTO) { // ✅ Nhận JSON từ body
        try {
            Ingredient updated = ingredientService.exportIngredient(id, logDTO.getQuantity(), logDTO.getCreatedBy());
            return ResponseEntity.ok(convertToDTO(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Integer id) {
        try {
            ingredientService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Phương thức chuyển đổi Entity -> DTO
    private IngredientDTO convertToDTO(Ingredient ingredient) {
        return modelMapper.map(ingredient, IngredientDTO.class);
    }
}
