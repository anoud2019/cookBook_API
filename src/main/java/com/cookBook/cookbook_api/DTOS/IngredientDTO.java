package com.cookBook.cookbook_api.DTOS;

import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.Utils.HelperUtils;


import java.util.HashSet;

import java.util.Set;

public class IngredientDTO {
    private Integer id;
    private String name;
    private Set<RecipeDTO> recipes;

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

    public Set<RecipeDTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<RecipeDTO> recipes) {
        this.recipes = recipes;
    }

    public static IngredientDTO convertToDTO(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        if (HelperUtils.isNotNull(ingredient)) {
            dto.setId(ingredient.getId());
            dto.setName(ingredient.getName());

            Set<RecipeDTO> recipeDTOs = new HashSet<>();
            if (HelperUtils.isNotNull(ingredient.getRecipes())) {
                for (Recipe recipe : ingredient.getRecipes()) {
                    RecipeDTO recipeDTO = new RecipeDTO();
                    recipeDTO.setId(recipe.getId());
                    recipeDTO.setName(recipe.getName());
                    recipeDTOs.add(recipeDTO);
                }
            }
            dto.setRecipes(recipeDTOs);
        }
        return dto;
    }
//    public static IngredientDTO convertToDTO(Ingredient ingredient) {
//        IngredientDTO ingredientDTO = new IngredientDTO();
//        if (HelperUtils.isNotNull(ingredient)) {
//            ingredientDTO.setId(ingredient.getId());
//            ingredientDTO.setName(ingredient.getName());
//
//            Set<RecipeDTO> recipeDTOList = new HashSet<>();
//            if (HelperUtils.isNotNull(ingredient.getRecipes()) && !ingredient.getRecipes().isEmpty()) {
//                for (Recipe recipe : ingredient.getRecipes()) {
//                    if (HelperUtils.isNotNull(recipe)) {
//                        RecipeDTO recipeDTO = new RecipeDTO();
//                        recipeDTO.setId(recipe.getId());
//                        recipeDTO.setName(recipe.getName());
//
//                        recipeDTOList.add(recipeDTO);
//                    }
//                }
//            }
//            ingredientDTO.setRecipes(recipeDTOList);
//        }
//        return ingredientDTO;
//    }

    public static Set<IngredientDTO> convertToDTO(Set<Ingredient> ingredientList) {
        Set<IngredientDTO> ingredientDTOList = new HashSet<>();
        if (!ingredientList.isEmpty()) {
            for (Ingredient i : ingredientList) {
                ingredientDTOList.add(convertToDTO(i));
            }
        }
        return ingredientDTOList;
    }

    //    public static Ingredient convertFromDTO(IngredientDTO dto) {
//        Ingredient entity = new Ingredient();
//        if (HelperUtils.isNotNull(dto)) {
//            entity.setName(dto.getName());
//
//
//            Set<Recipe> recipes = new HashSet<>();
//            if (HelperUtils.isNotNull(dto.getRecipes())) {
//                for (RecipeDTO recipeDTO : dto.getRecipes()) {
//                    Recipe recipe = RecipeDTO.convertFromDTO(recipeDTO);
//                    if (!recipe.getIngredients().contains(entity)) {
//                        recipe.getIngredients().add(entity);
//                    }
//
//                    recipes.add(recipe);
//                }
//            }
//            entity.setRecipes(recipes);
//        }
//        return entity;
//    }
    public static Ingredient convertFromDTO(IngredientDTO dto) {
        Ingredient entity = new Ingredient();
        if (HelperUtils.isNotNull(dto)) {
            entity.setName(dto.getName());

            Set<Recipe> recipes = new HashSet<>();
            if (HelperUtils.isNotNull(dto.getRecipes())) {
                for (RecipeDTO recipeDTO : dto.getRecipes()) {
                    if (HelperUtils.isNotNull(recipeDTO)) {
                        Recipe recipe = RecipeDTO.convertFromDTO(recipeDTO);
                        recipes.add(recipe);

                        if (!recipe.getIngredients().contains(entity)) {
                            recipe.getIngredients().add(entity);
                        }
                    }
                }
            }
            entity.setRecipes(recipes);
        }
        return entity;
    }


    public static Set<Ingredient> convertFromDTO(Set<IngredientDTO> ingredientDTOList) {
        Set<Ingredient> ingredientList = new HashSet<>();
        if (!ingredientDTOList.isEmpty()) {
            for (IngredientDTO dto : ingredientDTOList) {
                ingredientList.add(IngredientDTO.convertFromDTO(dto));
            }
        }
        return ingredientList;
    }

}
