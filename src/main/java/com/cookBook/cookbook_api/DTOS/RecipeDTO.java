package com.cookBook.cookbook_api.DTOS;

import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.Utils.HelperUtils;


import java.util.HashSet;

import java.util.Set;

public class RecipeDTO {
    private Integer id;

    private String name;
    private String instructions;

    private Set<IngredientDTO> ingredients;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Set<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }


//يشتغل
    public static RecipeDTO convertToDTO(Recipe recipe) {
        RecipeDTO recipeDTO = new RecipeDTO();
        if (HelperUtils.isNotNull(recipe)) {
            recipeDTO.setId(recipe.getId());
            recipeDTO.setName(recipe.getName());
            recipeDTO.setInstructions(recipe.getInstructions());

            Set<IngredientDTO> ingredientDTOList = new HashSet<>();
           // if (HelperUtils.isNotNull(recipe.getIngredients())) {
            if (HelperUtils.isNotNull(recipe.getIngredients()) && !recipe.getIngredients().isEmpty()){
                for (Ingredient ingredient : recipe.getIngredients()) {
                    IngredientDTO ingredientDTO = new IngredientDTO();
                    ingredientDTO.setId(ingredient.getId());
                    ingredientDTO.setName(ingredient.getName());
                    ingredientDTOList.add(ingredientDTO);
                }
            }
            recipeDTO.setIngredients(ingredientDTOList);
        }
        return recipeDTO;
    }

    //    public static Set<RecipeDTO> convertToDTO(Set<Recipe> recipeList) {
//        Set<RecipeDTO> recipeDTOList = new HashSet<>();
//        if (!recipeList.isEmpty()) {
//            for (Recipe r : recipeList) {
//                recipeDTOList.add(convertToDTO(r));
//            }
//        }
//        return recipeDTOList;
//    }
    public static Set<RecipeDTO> convertToDTO(Set<Recipe> recipeList) {
        Set<RecipeDTO> recipeDTOList = new HashSet<>();
        if (!recipeList.isEmpty()) {
            for (Recipe r : recipeList) {
                recipeDTOList.add(convertToDTO(r));
            }
        }
        return recipeDTOList;
    }

    public static Recipe convertFromDTO(RecipeDTO dto) {
        Recipe entity = new Recipe();
        if (HelperUtils.isNotNull(dto)) {
            entity.setName(dto.getName());
            entity.setInstructions(dto.getInstructions());

            Set<Ingredient> ingredients = new HashSet<>();
            if (HelperUtils.isNotNull(dto.getIngredients())) {
                for (IngredientDTO ingredientDTO : dto.getIngredients()) {
                    Ingredient ingredient = IngredientDTO.convertFromDTO(ingredientDTO);
                    if (!ingredient.getRecipes().contains(entity)) {
                        ingredient.getRecipes().add(entity);
                    }
                    ingredients.add(ingredient);
                }
            }
            entity.setIngredients(ingredients);
        }
        return entity;
    }


    public static Set<Recipe> convertFromDTO(Set<RecipeDTO> recipeDTOList) {
        Set<Recipe> recipeList = new HashSet<>();
        if (!recipeDTOList.isEmpty()) {
            for (RecipeDTO dto : recipeDTOList) {
                recipeList.add(RecipeDTO.convertFromDTO(dto));
            }
        }
        return recipeList;
    }

}
