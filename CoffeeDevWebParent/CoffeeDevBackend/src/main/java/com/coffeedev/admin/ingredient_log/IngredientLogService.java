package com.coffeedev.admin.ingredient_log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.coffeedev.common.entity.IngredientLog;

@Service
public class IngredientLogService {
    @Autowired
    private IngredientLogRepository ingredientLogRepo;

    public List<IngredientLog> getLogsByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endOfDay = calendar.getTime();

        return ingredientLogRepo.findByCreatedAtBetween(startOfDay, endOfDay);
    }
}
