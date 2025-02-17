package com.cookBook.cookbook_api.Repositories;

import com.cookBook.cookbook_api.Models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Set;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("SELECT r FROM Recipe r WHERE r.id = :id")
    Recipe getRecipeById(@Param("id") Integer id);

    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE i.name IN :ingredients")
    Set<Recipe> findRecipesByIngredients(@Param("ingredients") Set<String> ingredients);

    @Query("SELECT r FROM Recipe r WHERE r.name LIKE %:name%")


}
