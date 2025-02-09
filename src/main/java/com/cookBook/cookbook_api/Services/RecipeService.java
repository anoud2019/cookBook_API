package com.cookBook.cookbook_api.Services;

import com.cookBook.cookbook_api.DTOS.RecipeDTO;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.Repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public List<RecipeDTO> findRecipesByIngredients(List<String> ingredientNames) {
        List<Recipe> recipes = recipeRepository.findByIngredientsNameIn(ingredientNames);
        return RecipeDTO.convertToDTO(recipes);
    }

    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeDTO.convertFromDTO(recipeDTO);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return RecipeDTO.convertToDTO(savedRecipe);
    }

    public RecipeDTO updateRecipe(Integer id, RecipeDTO recipeDTO) {
        if (recipeDTO != null) {
            Optional<Recipe> existingRecipe = recipeRepository.findById(id);
            if (existingRecipe.isPresent()) {
                Recipe recipe = RecipeDTO.convertFromDTO(recipeDTO);
                recipe = recipeRepository.save(recipe);
                return RecipeDTO.convertToDTO(recipe);
            }
            return new RecipeDTO();
        }
    }
}

