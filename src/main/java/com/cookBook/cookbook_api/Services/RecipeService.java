package com.cookBook.cookbook_api.Services;

import com.cookBook.cookbook_api.DTOS.IngredientDTO;
import com.cookBook.cookbook_api.DTOS.RecipeDTO;
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
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    IngredientService ingredientService;

    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return RecipeDTO.convertToDTO(recipes);
    }

    public List<RecipeDTO> findRecipesByIngredients(List<String> ingredientNames) {
        List<Recipe> recipes = recipeRepository.findByIngredientsNameIn(ingredientNames);
        return RecipeDTO.convertToDTO(recipes);
    }

    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeDTO.convertFromDTO(recipeDTO);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return RecipeDTO.convertToDTO(savedRecipe);
    }


}

