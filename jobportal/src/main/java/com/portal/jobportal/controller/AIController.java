package com.portal.jobportal.controller;

import com.portal.jobportal.dto.CoverLetterRequest;
import com.portal.jobportal.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/generate-cover-letter")
    public ResponseEntity<String> generateCoverLetter(@RequestBody CoverLetterRequest request) {
        String result = aiService.generateCoverLetter(request);
        return ResponseEntity.ok(result);
    }
}