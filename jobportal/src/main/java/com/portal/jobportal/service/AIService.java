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

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateCoverLetter(CoverLetterRequest request) {
        String prompt = "Write a professional cover letter for " + request.getUserName() +
                " applying for the position of " + request.getJobTitle() +
                " at " + request.getCompanyName() + "." +
                "\n\nKey Skills: " + request.getUserSkills() +
                "\nJob Description: " + request.getJobDescription() +
                "\n\nKeep it professional, engaging, and under 250 words. Do not include placeholders like [Date] or [Address], start directly with 'Dear Hiring Manager,'.";

        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(content));
        String finalUrl = apiUrl + apiKey;

        try {
            Map<String, Object> response = restTemplate.postForObject(finalUrl, requestBody, Map.class);
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> firstCandidate = candidates.get(0);
            Map<String, Object> contentResponse = (Map<String, Object>) firstCandidate.get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) contentResponse.get("parts");

            return (String) parts.get(0).get("text");

        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating cover letter: " + e.getMessage();
        }
    }
}