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


    //    public RecipeDTO addRecipe(RecipeDTO dto) {
//
//
//        Recipe entity = RecipeDTO.convertFromDTO(dto);
//
//        //add ingredient to recipe
//        for (IngredientDTO ingredientDTO : dto.getIngredients()) {
//            Ingredient ingredient = IngredientDTO.convertFromDTO(ingredientDTO);
//            if (ingredient.getRecipes() == null) {
//                ingredient.setRecipes(new HashSet<>());
//            }
//            if (!ingredient.getRecipes().contains(entity)) {
//                ingredient.getRecipes().add(entity);
//                ingredientRepository.save(ingredient);
//            }
//        }
//        if (!ingredientRepository.existsById(entity.getId())) {
//            entity = recipeRepository.save(entity);
//        }
//        return RecipeDTO.convertToDTO(entity);
//
//    }
    public RecipeDTO addRecipe(RecipeDTO dto) {

        Recipe existingRecipe = recipeRepository.findByName(dto.getName());
        if (existingRecipe != null) {
            throw new RuntimeException("Recipe with the same name already exists.");
        }

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
            ingredients.add(ingredient);
        }
        recipe.setIngredients(ingredients);


        Recipe savedRecipe = recipeRepository.save(recipe);


        return RecipeDTO.convertToDTO(savedRecipe);
    }
//
//    public RecipeDTO updateRecipe(RecipeDTO dto) {
//        if (HelperUtils.isNotNull(dto)) {
//            Recipe entity = RecipeDTO.convertFromDTO(dto);
//            entity = recipeRepository.save(entity);
//            return RecipeDTO.convertToDTO(entity);
//        }
//
//        return new RecipeDTO();
//    }


    public RecipeDTO updateRecipe(RecipeDTO dto) {
        if (HelperUtils.isNotNull(dto)) {
            Recipe entity = RecipeDTO.convertFromDTO(dto);
            entity = recipeRepository.save(entity);
            return RecipeDTO.convertToDTO(entity);
        }

        return new RecipeDTO();
    }


    public Boolean deleteRecipe(Integer id) {
        if (HelperUtils.isNotNull(id) && recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<RecipeDTO> searchRecipesByIngredients(List<String> ingredients) {
        List<Recipe> recipes = recipeRepository.findRecipesByIngredients(ingredients);

        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeDTOList.add(RecipeDTO.convertToDTO(recipe));
        }

        return recipeDTOList;
    }

    public List<RecipeDTO> searchRecipesByName(String name) {

        List<Recipe> recipes = recipeRepository.findRecipesByNameContaining(name);

        List<RecipeDTO> recipeDTOList = new ArrayList<>();


        for (Recipe recipe : recipes) {
            recipeDTOList.add(RecipeDTO.convertToDTO(recipe));
        }

        return recipeDTOList;
    }


}

