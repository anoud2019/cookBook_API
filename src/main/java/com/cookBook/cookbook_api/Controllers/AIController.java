package com.cookBook.cookbook_api.Controllers;

import com.cookBook.cookbook_api.Services.AIService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "*")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/{chat}")
    public ResponseEntity<String> promptWithPathVariable(@PathVariable String chat) {
        try {

            String response = aiService.promptWithPathVariable(chat);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
