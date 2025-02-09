package com.cookBook.cookbook_api.Services;

import com.cookBook.cookbook_api.DTOS.IngredientDTO;
import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public List<IngredientDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return IngredientDTO.convertToDTO(ingredients);
    }
    public IngredientDTO addIngredient(IngredientDTO ingredientDTO){
        Ingredient ingredient=IngredientDTO.convertFromDTO(ingredientDTO);
        Ingredient saveIngredient=ingredientRepository.save(ingredient);
        return IngredientDTO.convertToDTO(saveIngredient);

    }


}
