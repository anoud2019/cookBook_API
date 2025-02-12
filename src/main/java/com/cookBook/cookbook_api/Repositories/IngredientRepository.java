package com.cookBook.cookbook_api.Repositories;

import com.cookBook.cookbook_api.Models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository  extends JpaRepository<Ingredient,Integer> {
    Ingredient findByName(String name);
}
