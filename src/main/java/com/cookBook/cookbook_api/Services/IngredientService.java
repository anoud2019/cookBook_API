package com.cookBook.cookbook_api.Services;

import com.cookBook.cookbook_api.DTOS.IngredientDTO;
import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.Repositories.IngredientRepository;
import com.cookBook.cookbook_api.Repositories.RecipeRepository;
import com.cookBook.cookbook_api.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    public List<IngredientDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return IngredientDTO.convertToDTO(ingredients);
    }

    public IngredientDTO addIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = IngredientDTO.convertFromDTO(ingredientDTO);
        List<Recipe> recipes = new ArrayList<>();
        for (String recipeId : ingredientDTO.getRecipes()) {
            Integer id = Integer.parseInt(recipeId);
            Recipe recipe = recipeRepository.findById(id).orElse(null);
            if (HelperUtils.isNotNull(recipe)) {
                recipes.add(recipe);
            }

        }
        ingredient.setRecipes(recipes);
        Ingredient saveIngredient = ingredientRepository.save(ingredient);
        return IngredientDTO.convertToDTO(saveIngredient);

    }


}
