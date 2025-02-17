package com.cookBook.cookbook_api.DTOS;

import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.Utils.HelperUtils;



public class RecipeDTO {
    private Integer id;

    private String name;
    private String instructions;


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

        return ingredients;
    }

        this.ingredients = ingredients;
    }


    public static RecipeDTO convertToDTO(Recipe recipe) {
        RecipeDTO recipeDTO = new RecipeDTO();
        if (HelperUtils.isNotNull(recipe)) {
            recipeDTO.setId(recipe.getId());
            recipeDTO.setName(recipe.getName());
            recipeDTO.setInstructions(recipe.getInstructions());

                for (Ingredient ingredient : recipe.getIngredients()) {
                }
            }
            recipeDTO.setIngredients(ingredientDTOList);
        }
        return recipeDTO;
    }

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

            if (HelperUtils.isNotNull(dto.getIngredients())) {
                for (IngredientDTO ingredientDTO : dto.getIngredients()) {
                    Ingredient ingredient = IngredientDTO.convertFromDTO(ingredientDTO);
                    ingredients.add(ingredient);
                }
            }
            entity.setIngredients(ingredients);
        }
        return entity;
    }


        if (!recipeDTOList.isEmpty()) {
            for (RecipeDTO dto : recipeDTOList) {
                recipeList.add(RecipeDTO.convertFromDTO(dto));
            }
        }
        return recipeList;
    }

}
