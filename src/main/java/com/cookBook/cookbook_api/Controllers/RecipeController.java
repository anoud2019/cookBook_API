package com.cookBook.cookbook_api.Controllers;

import com.cookBook.cookbook_api.DTOS.RecipeDTO;

import com.cookBook.cookbook_api.Services.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;


import java.util.Set;

@RestController
@RequestMapping(value = "recipes")
public class RecipeController {
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;


    @GetMapping(value = "/getAll")
    public Set<RecipeDTO> getAllRecipes() {
        Set<RecipeDTO> recipeDTOList = new HashSet<>();
        try {
            recipeDTOList.addAll(recipeService.getAllRecipes());
        } catch (Exception e) {
            logger.error("Error fetching all recipes: " + e.getMessage());
        }

        return recipeDTOList;
    }


    @GetMapping(value = "/getById")
    public RecipeDTO getRecipeById(@RequestParam(value = "recipeId") Integer id) {

        return recipeService.getRecipeById(id);

    }

    //    @PostMapping(value = "/add")
//    public RecipeDTO add(@RequestBody RecipeDTO dto) {
//        RecipeDTO recipeDTO = new RecipeDTO();
//        try {
//            recipeDTO = recipeService.addRecipe(dto);
//        } catch (Exception e) {
//            logger.error("Error: " + e.getMessage());
//        }
//        return recipeDTO;
//    }
    @PostMapping(value = "/add")
    public RecipeDTO add(@RequestBody RecipeDTO dto) {
        RecipeDTO recipeDTO = new RecipeDTO();
        try {
            recipeDTO = recipeService.addRecipe(dto);
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
        }
        return recipeDTO;
    }


    @PostMapping("update")
    public RecipeDTO update(@RequestBody RecipeDTO dto) {
        RecipeDTO recipeDTO = new RecipeDTO();
        try {
            recipeDTO = recipeService.updateRecipe(dto);
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
        }
        return recipeDTO;
    }

    //    @DeleteMapping(value = "/delete")
//    public Boolean deleteRecipe(@RequestBody RecipeDTO dto) {
//        try {
//            return recipeService.deleteRecipe(dto.getId());
//        } catch (Exception e) {
//            logger.error("Error: " + e.getMessage());
//            return false;
//
//        }
//    }
    @DeleteMapping(value = "/delete/{id}")
    public Boolean deleteRecipe(@PathVariable Integer id) {
        try {
            return recipeService.deleteRecipe(id);
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            return false;
        }
    }

    @GetMapping(value = "/searchRecipesByIngredients")
    public List<RecipeDTO> searchRecipesByIngredients(@RequestParam List<String> ingredients) {
        try {
            return recipeService.searchRecipesByIngredients(ingredients);
        } catch (Exception e) {
            logger.error("Error while searching recipes by ingredients: " + e.getMessage());

            return new ArrayList<>();
        }


    }

    @GetMapping(value = "/searchRecipesByName")
    public List<RecipeDTO> searchRecipesByName(@RequestParam String name) {
        try {
            return recipeService.searchRecipesByName(name);
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
