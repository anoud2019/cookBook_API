package com.cookBook.cookbook_api.Controllers;

import com.cookBook.cookbook_api.DTOS.IngredientDTO;

import com.cookBook.cookbook_api.Services.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;

import java.util.Set;

@RestController
@RequestMapping(value = "/ingredients")
@CrossOrigin("*")
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
    public Set<IngredientDTO> getAllIngredients() {
        Set<IngredientDTO> ingredientDTOList = new HashSet<>();
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


    @PutMapping(value = "/update/{id}")
    public IngredientDTO update(@PathVariable Integer id, @RequestBody IngredientDTO dto) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        try {

            ingredientDTO = ingredientService.updateIngredient(id, dto);
        } catch (Exception e) {

            logger.error("Error updating ingredient: ", e);
        }
        return ingredientDTO;
    }


    @DeleteMapping(value = "/delete/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return ingredientService.deleteIngredient(id);
    }

}


