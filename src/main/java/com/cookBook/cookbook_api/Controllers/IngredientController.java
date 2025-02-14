package com.cookBook.cookbook_api.Controllers;

import com.cookBook.cookbook_api.DTOS.IngredientDTO;
import com.cookBook.cookbook_api.DTOS.RecipeDTO;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.RequestObject.AddIngredientRequest;
import com.cookBook.cookbook_api.Services.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/ingredients")
public class IngredientController {
    private final Logger logger = LoggerFactory.getLogger(IngredientController.class);

    @Autowired
    private IngredientService ingredientService;

    @PostMapping(value = "/add")
    public IngredientDTO add(@RequestBody IngredientDTO dto) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        try {
            ingredientDTO = ingredientService.addIngredient(dto);
        } catch (Exception e) {
            logger.error("Error adding ingredient: ", e);

        }
        return ingredientDTO;
    }


    @GetMapping(value = "/getAll")
    public List<IngredientDTO> getAllIngredients() {
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        try {
            ingredientDTOList.addAll(ingredientService.getAllIngredients());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ingredientDTOList;
    }

    @GetMapping(value = "getById")
    public IngredientDTO getIngredientById(@RequestParam(value = "ingredientId") Integer id) {
        return ingredientService.getIngredientById(id);
    }

    @PostMapping(value = "/update")
    public IngredientDTO update(@RequestBody IngredientDTO dto) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        try {

            ingredientDTO = ingredientService.updateIngredient(dto);
        } catch (Exception e) {
            logger.error("Error updating ingredient: ", e);
        }
        return ingredientDTO;
    }

    //        @DeleteMapping(value = "/delete")
//    public Boolean delete(@RequestBody IngredientDTO dto) {
//        try {
//            return ingredientService.deleteIngredient(dto.getId());
//        } catch (Exception e) {
//            logger.error("Error deleting ingredient: ", e);
//        }
//        return false;
//    }
    @DeleteMapping(value = "/delete/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return ingredientService.deleteIngredient(id);
    }

}


