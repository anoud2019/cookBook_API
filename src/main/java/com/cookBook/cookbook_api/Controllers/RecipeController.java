package com.cookBook.cookbook_api.Controllers;

import com.cookBook.cookbook_api.DTOS.RecipeDTO;

import com.cookBook.cookbook_api.Services.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;


import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "recipes")
@CrossOrigin("*")
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


    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> serveImage(@PathVariable String fileName) throws IOException {
        Path imagePath = Paths.get("uploads").resolve(fileName).normalize();
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            throw new RuntimeException("Image not found: " + fileName);
        }
    }

    @GetMapping(value = "/getRecipeById/{id}")
    public RecipeDTO getRecipeById(@PathVariable Integer id) {
        return recipeService.getRecipeById(id);
    }



    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public RecipeDTO add(
            @RequestPart("dto") RecipeDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            return recipeService.addRecipe(dto, file);
        } catch (Exception e) {
            logger.error("Error adding recipe: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add recipe", e);
        }
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateRecipe(
            @PathVariable Integer id,
            @RequestPart("dto") RecipeDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            recipeService.updateRecipe(id, dto, file);

            // Return success message
            Map<String, String> response = new HashMap<>();
            response.put("message", "Recipe updated successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update recipe", e);
        }
    }


    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteRecipe(@PathVariable Integer id) {
        try {
            return recipeService.deleteRecipe(id);
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            return false;
        }
    }

    @GetMapping(value = "/searchRecipesByIngredients")
    public Set<RecipeDTO> searchRecipesByIngredients(@RequestParam Set<String> ingredients) {
        try {
            return recipeService.searchRecipesByIngredients(ingredients);
        } catch (Exception e) {
            logger.error("Error while searching recipes by ingredients: " + e.getMessage());

            return new HashSet<>();
        }


    }

    @GetMapping(value = "/searchRecipesByName")
    public Set<RecipeDTO> searchRecipesByName(@RequestParam String name) {
        try {
            return recipeService.searchRecipesByName(name);
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            return new HashSet<>();
        }
    }

}
