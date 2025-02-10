package com.cookBook.cookbook_api.Repositories;

import com.cookBook.cookbook_api.Models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByIngredientsNameIn(List<String> ingredientNames);
}
