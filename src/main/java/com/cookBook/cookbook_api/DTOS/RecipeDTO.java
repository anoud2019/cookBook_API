package com.cookBook.cookbook_api.DTOS;

import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Models.Recipe;


import java.util.ArrayList;
import java.util.List;

public class RecipeDTO {
    private Integer id;

    private String name;
    private String instructions;

    private List<String> ingredients;

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
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
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
        recipeDTO.setId(recipe.getId());
        recipeDTO.setName(recipe.getName());
        recipeDTO.setInstructions(recipe.getInstructions());

        List<String> ingredientNames = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredientNames.add(ingredient.getName());
        }
        recipeDTO.setIngredients(ingredientNames);

        return recipeDTO;
    }

    public static List<RecipeDTO> convertToDTO(List<Recipe> recipeList) {
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            recipeDTOList.add(convertToDTO(recipe));
        }
        return recipeDTOList;
    }
    public static Recipe convertFromDTO(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDTO.getId());
        recipe.setName(recipeDTO.getName());
        recipe.setInstructions(recipeDTO.getInstructions());


        List<Ingredient> ingredients = new ArrayList<>();
        for (String ingredientName : recipeDTO.getIngredients()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientName);
            ingredients.add(ingredient);
        }
        recipe.setIngredients(ingredients);

        return recipe;
    }

    public static List<Recipe> convertFromDTO(List<RecipeDTO> recipeDTOList) {
        List<Recipe> recipeList = new ArrayList<>();
        for (RecipeDTO recipeDTO : recipeDTOList) {
            Recipe recipe=convertFromDTO(recipeDTO);
            recipeList.add(recipe);
        }
        return recipeList;
    }

}
