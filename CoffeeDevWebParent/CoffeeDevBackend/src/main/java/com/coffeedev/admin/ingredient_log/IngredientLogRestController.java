package com.coffeedev.admin.ingredient_log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.coffeedev.common.dto.IngredientLogDTO;
import com.coffeedev.common.entity.IngredientLog;

@RestController
@RequestMapping("/api/ingredient-logs")
public class IngredientLogRestController {
    @Autowired
    private IngredientLogService ingredientLogService;

    @GetMapping
    public List<IngredientLogDTO> getLogsByDate(@RequestParam String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = sdf.parse(date);
        List<IngredientLog> logs = ingredientLogService.getLogsByDate(parsedDate);

        // ✅ Chuyển đổi từ `IngredientLog` sang `IngredientLogDTO`
        return logs.stream().map(IngredientLogDTO::new).collect(Collectors.toList());
    }
}
