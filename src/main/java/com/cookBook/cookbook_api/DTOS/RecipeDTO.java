package com.cookBook.cookbook_api.DTOS;

import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.Utils.HelperUtils;


import java.util.ArrayList;
import java.util.List;

public class RecipeDTO {
    private Integer id;

    private String name;
    private String instructions;

    private List<String> ingredients;
    private List<IngredientDTO> ingredients;

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

    public List<String> getIngredients() {
    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "RecipeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    public static RecipeDTO convertToDTO(Recipe recipe) {
        RecipeDTO recipeDTO = new RecipeDTO();
        if (HelperUtils.isNotNull(recipe)) {
            recipeDTO.setId(recipe.getId());
            recipeDTO.setName(recipe.getName());
            recipeDTO.setInstructions(recipe.getInstructions());

            List<String> ingredientNames = new ArrayList<>();
            List<IngredientDTO> ingredientDTOList = new ArrayList<>();
            if (HelperUtils.isNotNull(recipe.getIngredients())) {
                for (Ingredient ingredient : recipe.getIngredients()) {
                    ingredientNames.add(ingredient.getName());
                    ingredientDTOList.add(IngredientDTO.convertToDTO(ingredient));
                }
            }
            recipeDTO.setIngredients(ingredientNames);
            recipeDTO.setIngredients(ingredientDTOList);
        }

        return recipeDTO;
    }

    public static List<RecipeDTO> convertToDTO(List<Recipe> recipeList) {
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        if (HelperUtils.isNotNull(recipeList) && !recipeList.isEmpty()) {
            for (Recipe recipe : recipeList) {
                recipeDTOList.add(convertToDTO(recipe));
        if (!recipeList.isEmpty()) {
            for (Recipe r : recipeList) {
                recipeDTOList.add(convertToDTO(r));
            }
        }
        return recipeDTOList;
    }


    public static Recipe convertFromDTO(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        if (HelperUtils.isNotNull(recipeDTO)) {
            recipe.setId(recipeDTO.getId());
            recipe.setName(recipeDTO.getName());
            recipe.setInstructions(recipeDTO.getInstructions());

    public static Recipe convertFromDTO(RecipeDTO dto) {
        Recipe entity = new Recipe();
        if (HelperUtils.isNotNull(dto)) {
            entity.setName(dto.getName());
            entity.setInstructions(dto.getInstructions());

            List<Ingredient> ingredients = new ArrayList<>();
            if (HelperUtils.isNotNull(recipeDTO.getIngredients())) {
                for (String ingredientName : recipeDTO.getIngredients()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingredientName);
            if (HelperUtils.isNotNull(dto.getIngredients())) {
                for (IngredientDTO ingredientDTO : dto.getIngredients()) {
                    Ingredient ingredient = IngredientDTO.convertFromDTO(ingredientDTO);
                    ingredient.getRecipes().add(entity);
                    ingredients.add(ingredient);
                }
            }
            recipe.setIngredients(ingredients);
            entity.setIngredients(ingredients);
        }
        return recipe;
        return entity;
    }


    public static List<Recipe> convertFromDTO(List<RecipeDTO> recipeDTOList) {
        List<Recipe> recipeList = new ArrayList<>();
        if (!recipeList.isEmpty()) {
            for (RecipeDTO recipeDTO : recipeDTOList) {
                // Recipe recipe = convertFromDTO(recipeDTO);
                recipeList.add(RecipeDTO.convertFromDTO(recipeDTO));
        if (!recipeDTOList.isEmpty()) {
            for (RecipeDTO dto : recipeDTOList) {
                recipeList.add(RecipeDTO.convertFromDTO(dto));
            }
        }
        return recipeList;
    }

}
