package com.cookBook.cookbook_api.Services;

import com.cookBook.cookbook_api.DTOS.IngredientDTO;
import com.cookBook.cookbook_api.DTOS.RecipeDTO;
import com.cookBook.cookbook_api.Models.Ingredient;
import com.cookBook.cookbook_api.Models.Recipe;
import com.cookBook.cookbook_api.Repositories.IngredientRepository;
import com.cookBook.cookbook_api.Repositories.RecipeRepository;
import com.cookBook.cookbook_api.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

import java.util.List;
import java.util.Set;


@Service
public class RecipeService {
    private static final String UPLOAD_DIR = "images/";
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    IngredientService ingredientService;

    public Set<RecipeDTO> getAllRecipes() {
        List<Recipe> recipeList = recipeRepository.findAll();
        Set<Recipe> recipes = new HashSet<>(recipeList);
        return RecipeDTO.convertToDTO(recipes);
    }

    public RecipeDTO getRecipeById(Integer id) {
        Recipe entity = recipeRepository.getRecipeById(id);
        return RecipeDTO.convertToDTO(entity);
    }

    public RecipeDTO addRecipe(RecipeDTO dto, MultipartFile file) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setInstructions(dto.getInstructions());


        Set<Ingredient> ingredients = new HashSet<>();
        for (IngredientDTO ingredientDTO : dto.getIngredients()) {
            Ingredient ingredient;
            if (ingredientDTO.getId() != null) {

                ingredient = ingredientRepository.findById(ingredientDTO.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));
            } else {

                ingredient = new Ingredient();
                ingredient.setName(ingredientDTO.getName());
                ingredient = ingredientRepository.save(ingredient);
            }
            ingredients.add(ingredient);
        }
        recipe.setIngredients(ingredients);


        if (file != null && !file.isEmpty()) {
            try {
                recipe.setImageUrl(saveUploadedFile(file));
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }


        return RecipeDTO.convertToDTO(recipeRepository.save(recipe));
    }

    public void updateRecipe(Integer id, RecipeDTO dto, MultipartFile file) {
        // Fetch the current recipe from the database
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with ID: " + id));

        // Update fields only if they are not empty
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            recipe.setName(dto.getName());
        }
        if (dto.getInstructions() != null && !dto.getInstructions().isEmpty()) {
            recipe.setInstructions(dto.getInstructions());
        }

        // Update ingredients if provided
        if (dto.getIngredients() != null && !dto.getIngredients().isEmpty()) {
            Set<Ingredient> ingredients = new HashSet<>();
            for (IngredientDTO ingredientDTO : dto.getIngredients()) {
                Ingredient ingredient;
                if (ingredientDTO.getId() != null) {
                    ingredient = ingredientRepository.findById(ingredientDTO.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));
                } else {
                    ingredient = new Ingredient();
                    ingredient.setName(ingredientDTO.getName());
                    ingredient = ingredientRepository.save(ingredient);
                }
                ingredients.add(ingredient);
            }
            recipe.setIngredients(ingredients);
        }

        // Update the image if a new file is uploaded
        if (file != null && !file.isEmpty()) {
            try {
                recipe.setImageUrl(saveUploadedFile(file));
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }

        // Save the updated recipe
        recipeRepository.save(recipe);
    }

    @Value("${server.url}")
    private String serverUrl;

    private String saveUploadedFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), uploadPath.resolve(fileName));


        return serverUrl + "/recipes/images/" + fileName;
    }


    public Boolean deleteRecipe(Integer id) {
        if (HelperUtils.isNotNull(id) && recipeRepository.existsById(id)) {
            Recipe recipe = recipeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.getRecipes().remove(recipe);
                ingredientRepository.save(ingredient);
            }

            recipeRepository.deleteById(id);

            return true;
        }
        return false;
    }


    public Set<RecipeDTO> searchRecipesByIngredients(Set<String> ingredients) {
        Set<Recipe> recipes = recipeRepository.findRecipesByIngredients(ingredients);

        Set<RecipeDTO> recipeDTOList = new HashSet<>();
        for (Recipe recipe : recipes) {
            recipeDTOList.add(RecipeDTO.convertToDTO(recipe));
        }

        return recipeDTOList;
    }

    public Set<RecipeDTO> searchRecipesByName(String name) {

        Set<Recipe> recipes = recipeRepository.findRecipesByNameContaining(name);

        Set<RecipeDTO> recipeDTOList = new HashSet<>();


        for (Recipe recipe : recipes) {
            recipeDTOList.add(RecipeDTO.convertToDTO(recipe));
        }

        return recipeDTOList;
    }


}

