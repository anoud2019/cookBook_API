package com.cookBook.cookbook_api.Services;

import com.cookBook.cookbook_api.DTOS.IngredientDTO;
import com.cookBook.cookbook_api.DTOS.RecipeDTO;
import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.Repositories.IngredientRepository;
import com.cookBook.cookbook_api.Repositories.RecipeRepository;
import com.cookBook.cookbook_api.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    public Set<IngredientDTO> getAllIngredients() {
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        Set<Ingredient> ingredients = new HashSet<>(ingredientList);
        return IngredientDTO.convertToDTO(ingredients);
    }

    public IngredientDTO getIngredientById(Integer id) {
        Ingredient entity = ingredientRepository.getIngredientById(id);
        return IngredientDTO.convertToDTO(entity);
    }

    //يشتغل
//            // التحقق من أن recipes ليست null

    public IngredientDTO addIngredient(IngredientDTO dto) {
            // تحويل DTO إلى Entity

            // التحقق من أن recipes ليست null
        Ingredient existingIngredient = ingredientRepository.findByName(dto.getName());
        if (existingIngredient != null) {

            return IngredientDTO.convertToDTO(existingIngredient);
        }

            // التحقق من وجود الكيان في قاعدة البيانات

                // التحقق من أن الكيان تم حفظه بنجاح

            // تحويل DTO إلى Entity

            // التحقق من أن recipes ليست null
        if (dto.getRecipes() != null) {
            for (RecipeDTO recipeDTO : dto.getRecipes()) {
                if (HelperUtils.isNotNull(recipeDTO.getId())) {
                    Recipe recipe = recipeRepository.findById(recipeDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Recipe not found"));
                    recipe.getIngredients().add(entity);
                    recipeRepository.save(recipe);
                }
            }
        }

            // حفظ الكيان في قاعدة البيانات

            // التحقق من أن الكيان تم حفظه بنجاح
        entity = ingredientRepository.save(entity);


    public Boolean deleteIngredient(Integer id) {
        if (HelperUtils.isNotNull(id) && ingredientRepository.existsById(id)) {
            ingredientRepository.deleteById(id);
            return true;
        }
        return false;
    }


//    public IngredientDTO updateIngredient(IngredientDTO dto) {
//        if (HelperUtils.isNotNull(dto)) {
//            Ingredient entity = IngredientDTO.convertFromDTO(dto);
//            entity = ingredientRepository.save(entity);
//            return IngredientDTO.convertToDTO(entity);
//        }
//        return new IngredientDTO();
//    }

    public IngredientDTO updateIngredient(Integer id, IngredientDTO dto) {
        if (HelperUtils.isNotNull(id)) {
            // التحقق مما إذا كان المكون موجودًا بالفعل
            Ingredient existingIngredient = ingredientRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ingredient not found with ID: " + id));

            // تحديث بيانات المكون الأساسية
            existingIngredient.setName(dto.getName());

            // تحديث الوصفات المرتبطة بالمكون (إذا كانت موجودة)
            Set<Recipe> updatedRecipes = new HashSet<>();
            if (HelperUtils.isNotNull(dto.getRecipes()) && !dto.getRecipes().isEmpty()) {
                for (RecipeDTO recipeDTO : dto.getRecipes()) {
                    Recipe recipe;
                    if (recipeDTO.getId() != null) {
                        // إذا كانت الوصفة موجودة بالفعل في قاعدة البيانات
                        recipe = recipeRepository.findById(recipeDTO.getId())
                                .orElseThrow(() -> new RuntimeException("Recipe not found"));
                    } else {
                        // إذا كانت الوصفة جديدة
                        recipe = new Recipe();
                        recipe.setName(recipeDTO.getName());
                        recipe.setInstructions(recipeDTO.getInstructions());
                        recipe = recipeRepository.save(recipe); // حفظ الوصفة الجديدة
                    }
                    updatedRecipes.add(recipe);
                }
            }

            // تحديث الوصفات المرتبطة بالمكون
            existingIngredient.setRecipes(updatedRecipes);

            // حفظ التغييرات في قاعدة البيانات
            Ingredient updatedIngredient = ingredientRepository.save(existingIngredient);

            // تحويل الكيان المحدث إلى DTO وإرجاعه
            return IngredientDTO.convertToDTO(updatedIngredient);
        } else {
            throw new RuntimeException("Invalid Ingredient ID provided.");
        }
    }


}
