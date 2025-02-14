package com.cookBook.cookbook_api.Controllers;

import com.cookBook.cookbook_api.DTOS.IngredientDTO;
import com.cookBook.cookbook_api.DTOS.RecipeDTO;
import com.cookBook.cookbook_api.Services.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final Logger logger = LoggerFactory.getLogger(IngredientController.class);

    @Autowired
    private IngredientService ingredientService;

    @PostMapping(value = "/addIngredients")
    public IngredientDTO add(@RequestBody IngredientDTO dto) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        try {
            ingredientDTO = ingredientService.addIngredient(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return ingredientDTO;
    }


}
