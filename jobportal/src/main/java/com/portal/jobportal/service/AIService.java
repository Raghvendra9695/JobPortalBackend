package com.portal.jobportal.service;

import com.portal.jobportal.dto.CoverLetterRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {
    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateCoverLetter(CoverLetterRequest request) {

        String skills = (request.getUserSkills() != null && !request.getUserSkills().isEmpty()) ? request.getUserSkills() : "Java";
        String jobDesc = (request.getJobDescription() != null && !request.getJobDescription().isEmpty()) ? request.getJobDescription() : "Developer";

        String prompt = "Write a short professional cover letter for " + request.getUserName() +
                " applying for " + request.getJobTitle() + " at " + request.getCompanyName() + "." +
                "\n\nSkills: " + skills +
                "\nJob Description: " + jobDesc +
                "\n\nOutput only the letter body. Keep it under 200 words.";

        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(content));
        String validKey = (apiKey != null) ? apiKey.trim() : "";
        String finalUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + validKey;

        try {
            System.out.println("Calling AI Service...");
            Map<String, Object> response = restTemplate.postForObject(finalUrl, requestBody, Map.class);

            if (response != null && response.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> firstCandidate = candidates.get(0);
                    Map<String, Object> contentResponse = (Map<String, Object>) firstCandidate.get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) contentResponse.get("parts");
                    return (String) parts.get(0).get("text");
                }
            }
            return "Error: Empty response from AI.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to generate: " + e.getMessage();
        }
    }
}