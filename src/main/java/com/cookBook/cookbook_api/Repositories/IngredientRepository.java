package com.cookBook.cookbook_api.Repositories;


import com.cookBook.cookbook_api.Models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    @Query("SELECT i FROM Ingredient i WHERE i.id = :id")
    Ingredient getIngredientById(@Param("id") Integer id);

    @Query("SELECT i FROM Ingredient i WHERE LOWER(i.name) = LOWER(:name)")
    Ingredient findByName(@Param("name") String name);


}
