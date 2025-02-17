package com.cookBook.cookbook_api.DTOS;

import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.Utils.HelperUtils;


public class IngredientDTO {
    private Integer id;
    private String name;

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

        return recipes;
    }

        this.recipes = recipes;
    }

    public static IngredientDTO convertToDTO(Ingredient ingredient) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        if (HelperUtils.isNotNull(ingredient)) {
            ingredientDTO.setId(ingredient.getId());
            ingredientDTO.setName(ingredient.getName());

            if (HelperUtils.isNotNull(ingredient.getRecipes()) && !ingredient.getRecipes().isEmpty()) {
                for (Recipe recipe : ingredient.getRecipes()) {
                    recipeDTOList.add(RecipeDTO.convertToDTO(recipe));
                }
            }


            ingredientDTO.setRecipes(recipeDTOList);
        }
        return ingredientDTO;
    }
        if (!ingredientList.isEmpty()) {
            for (Ingredient i : ingredientList) {
                ingredientDTOList.add(convertToDTO(i));
            }
        }
        return ingredientDTOList;
    }

    public static Ingredient convertFromDTO(IngredientDTO dto) {
        Ingredient entity = new Ingredient();
        if (HelperUtils.isNotNull(dto)) {
            entity.setName(dto.getName());

            if (HelperUtils.isNotNull(dto.getRecipes())) {
                for (RecipeDTO recipeDTO : dto.getRecipes()) {
                    Recipe recipe = RecipeDTO.convertFromDTO(recipeDTO);
                    recipe.getIngredients().add(entity);
                    recipes.add(recipe);
                }
            }
            entity.setRecipes(recipes);
        }
        return entity;
    }


        if (!ingredientDTOList.isEmpty()) {
            for (IngredientDTO dto : ingredientDTOList) {
                ingredientList.add(IngredientDTO.convertFromDTO(dto));
            }
        }
        return ingredientList;
    }

}
