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


import java.util.HashSet;

import java.util.List;
import java.util.Set;


@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    IngredientService ingredientService;

    public Set<RecipeDTO> getAllRecipes() {
        List<Recipe> recipeList = recipeRepository.findAll();
        Set<Recipe> recipes = new HashSet<>(recipeList);
        return RecipeDTO.convertToDTO(recipes);
    }

    public RecipeDTO getRecipeById(Integer id) {
        Recipe entity = recipeRepository.getRecipeById(id);
        return RecipeDTO.convertToDTO(entity);
    }


    public RecipeDTO addRecipe(RecipeDTO dto) {

        Recipe existingRecipe = recipeRepository.findByName(dto.getName());
        if (existingRecipe != null) {
            throw new RuntimeException("Recipe with the same name already exists.");
        }

        // إنشاء وصفة جديدة
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setInstructions(dto.getInstructions());

        Set<Ingredient> ingredients = new HashSet<>();
        for (IngredientDTO ingredientDTO : dto.getIngredients()) {
            Ingredient ingredient;
            if (ingredientDTO.getId() != null) {

                ingredient = ingredientRepository.findById(ingredientDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found"));
            } else {

                ingredient = new Ingredient();
                ingredient.setName(ingredientDTO.getName());
                ingredient = ingredientRepository.save(ingredient);
            }

            if (!ingredient.getRecipes().contains(recipe)) {
                ingredient.getRecipes().add(recipe);
                ingredientRepository.save(ingredient);
            }
            ingredients.add(ingredient);
        }
        recipe.setIngredients(ingredients);


        Recipe savedRecipe = recipeRepository.save(recipe);


        return RecipeDTO.convertToDTO(savedRecipe);
    }

    public RecipeDTO updateRecipe(Integer id, RecipeDTO dto) {
        if (HelperUtils.isNotNull(id)) {

            Recipe existingRecipe = recipeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + id));


            existingRecipe.setName(dto.getName());
            existingRecipe.setInstructions(dto.getInstructions());


            Set<Ingredient> updatedIngredients = new HashSet<>();
            for (IngredientDTO ingredientDTO : dto.getIngredients()) {
                Ingredient ingredient;
                if (ingredientDTO.getId() != null) {

                    ingredient = ingredientRepository.findById(ingredientDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Ingredient not found"));
                } else {

                    ingredient = new Ingredient();
                    ingredient.setName(ingredientDTO.getName());
                    ingredient = ingredientRepository.save(ingredient);
                }
                if (!ingredient.getRecipes().contains(existingRecipe)) {
                    ingredient.getRecipes().add(existingRecipe);
                    ingredientRepository.save(ingredient);
                }
                updatedIngredients.add(ingredient);
            }


            existingRecipe.setIngredients(updatedIngredients);


            Recipe updatedRecipe = recipeRepository.save(existingRecipe);


            return RecipeDTO.convertToDTO(updatedRecipe);
        } else {
            throw new RuntimeException("Invalid Recipe ID provided.");
        }
    }


    public Boolean deleteRecipe(Integer id) {
        if (HelperUtils.isNotNull(id) && recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Set<RecipeDTO> searchRecipesByIngredients(Set<String> ingredients) {
        Set<Recipe> recipes = recipeRepository.findRecipesByIngredients(ingredients);

        Set<RecipeDTO> recipeDTOList = new HashSet<>();
        for (Recipe recipe : recipes) {
            recipeDTOList.add(RecipeDTO.convertToDTO(recipe));
        }

        return recipeDTOList;
    }

    public Set<RecipeDTO> searchRecipesByName(String name) {

        Set<Recipe> recipes = recipeRepository.findRecipesByNameContaining(name);

        Set<RecipeDTO> recipeDTOList = new HashSet<>();


        for (Recipe recipe : recipes) {
            recipeDTOList.add(RecipeDTO.convertToDTO(recipe));
        }

        return recipeDTOList;
    }


}

