package com.coffeedev.admin.ingredient;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.coffeedev.common.entity.Ingredient;

@Repository
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Integer> {

    public Iterable<Ingredient> findAll();

    public Optional<Ingredient> findById(Integer id);

    public Ingredient save(Ingredient ingredient);

    public void deleteById(Integer id);
}
