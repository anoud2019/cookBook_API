package com.cookBook.cookbook_api.DTOS;

import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.Utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;

public class IngredientDTO {
    private Integer id;
    private String name;
    private List<RecipeDTO> recipes;

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

    public List<RecipeDTO> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeDTO> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return "IngredientDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recipes=" + recipes +
                '}';
    }

    public static IngredientDTO convertToDTO(Ingredient ingredient) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        if (HelperUtils.isNotNull(ingredient))
        if (HelperUtils.isNotNull(ingredient)) {
            ingredientDTO.setId(ingredient.getId());
        ingredientDTO.setName(ingredient.getName());
            ingredientDTO.setName(ingredient.getName());


        List<String> recipeNames = new ArrayList<>();
        if (HelperUtils.isNotNull(ingredient.getRecipes())) {
            //if (ingredient.getRecipes() != null) {
            for (Recipe recipe : ingredient.getRecipes()) {
                recipeNames.add(recipe.getName());
            List<RecipeDTO> recipeDTOList = new ArrayList<>();
            if (HelperUtils.isNotNull(ingredient.getRecipes()) && !ingredient.getRecipes().isEmpty()) {
                for (Recipe recipe : ingredient.getRecipes()) {
                    recipeDTOList.add(RecipeDTO.convertToDTO(recipe));
                }
            }
        }
        ingredientDTO.setRecipes(recipeNames);


            ingredientDTO.setRecipes(recipeDTOList);
        }
        return ingredientDTO;
    }

    public static List<IngredientDTO> convertToDTO(List<Ingredient> ingredientList) {
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        if (ingredientList.isEmpty()) {

            for (Ingredient ingredient : ingredientList) {
                ingredientDTOList.add(convertToDTO(ingredient));
        if (!ingredientList.isEmpty()) {
            for (Ingredient i : ingredientList) {
                ingredientDTOList.add(convertToDTO(i));
            }
        }
        return ingredientDTOList;
    }

    public static Ingredient convertFromDTO(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        if (HelperUtils.isNotNull(ingredientDTO)) {
            //ingredient.setId(ingredientDTO.getId());
            ingredient.setName(ingredientDTO.getName());
    public static Ingredient convertFromDTO(IngredientDTO dto) {
        Ingredient entity = new Ingredient();
        if (HelperUtils.isNotNull(dto)) {
            entity.setName(dto.getName());


            List<Recipe> recipes = new ArrayList<>();
            if (HelperUtils.isNotNull(dto.getRecipes())) {
                for (RecipeDTO recipeDTO : dto.getRecipes()) {
                    Recipe recipe = RecipeDTO.convertFromDTO(recipeDTO);
                    recipe.getIngredients().add(entity);
                    recipes.add(recipe);
                }
            }
            entity.setRecipes(recipes);
        }
        return ingredient;
        return entity;
    }


    public static List<Ingredient> convertFromDTO(List<IngredientDTO> ingredientDTOList) {
        List<Ingredient> ingredientList = new ArrayList<>();
        if (!ingredientDTOList.isEmpty()) {
            for (IngredientDTO ingredientDTO : ingredientDTOList) {
                Ingredient ingredient = convertFromDTO(ingredientDTO);
                ingredientList.add(ingredient);
            for (IngredientDTO dto : ingredientDTOList) {
                ingredientList.add(IngredientDTO.convertFromDTO(dto));
            }
        }
        return ingredientList;
    }

}
