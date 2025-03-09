package com.cookBook.cookbook_api.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIService {
    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    private final RestTemplate restTemplate;
    private final String GEMINI_API_KEY = "AIzaSyD3SGTfICPMBt5Vv9mGo2oXP86yTMGiBZc";
    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + GEMINI_API_KEY;

    public AIService() {
        this.restTemplate = new RestTemplate();
    }

    public String promptWithPathVariable(String chat) {
        try {
            logger.info("Chat data: " + chat);
            String response = callGeminiAPI(chat);
            logger.info("Response: " + response);
            return response;
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    public String callGeminiAPI(String query) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");


        String requestBody = "{ \"contents\": [{ \"parts\": [{\"text\": \"" + query + "\"}] }] }";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);


        ResponseEntity<String> response = restTemplate.exchange(GEMINI_API_URL, HttpMethod.POST, entity, String.class);


        return extractTextFromResponse(response.getBody());
    }

    private String extractTextFromResponse(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);


            return rootNode.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response";
        }
    }


}
