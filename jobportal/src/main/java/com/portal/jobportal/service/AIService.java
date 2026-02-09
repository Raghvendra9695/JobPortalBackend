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
        String skills = (request.getUserSkills() != null && !request.getUserSkills().isEmpty()) ? request.getUserSkills() : "Java, Communication";
        String jobDesc = (request.getJobDescription() != null && !request.getJobDescription().isEmpty()) ? request.getJobDescription() : "Software Engineer role";
        String prompt = "Write a short, professional cover letter for " + request.getUserName() +
                " applying for " + request.getJobTitle() + " at " + request.getCompanyName() + "." +
                "\n\nSkills: " + skills +
                "\nJob Requirements: " + jobDesc +
                "\n\nKeep it under 200 words. Start with 'Dear Hiring Manager,'.";

        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(content));
        String validKey = (apiKey != null) ? apiKey.trim() : "";
        String finalUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + validKey;

        try {
            System.out.println("Calling Gemini API...");

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
            return "AI returned an empty response. Please try again.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error from AI Service: " + e.getMessage();
        }
    }
}