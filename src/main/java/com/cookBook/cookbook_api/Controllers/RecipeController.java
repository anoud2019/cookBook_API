package com.cookBook.cookbook_api.Controllers;

import com.cookBook.cookbook_api.DTOS.RecipeDTO;
import com.cookBook.cookbook_api.Services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping(value = "/getAll")
    public List<RecipeDTO> getAllRecipes() {
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        try {
            recipeDTOList.addAll(recipeService.getAllRecipes());
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return recipeDTOList;
    }

    @PostMapping(value = "/add")
    public RecipeDTO add(@RequestBody RecipeDTO dto) {
        RecipeDTO recipeDTO = new RecipeDTO();
        try {
            recipeDTO = recipeService.addRecipe(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return recipeDTO;
    }
}
