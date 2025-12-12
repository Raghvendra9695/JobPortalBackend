package com.portal.jobportal.controller;

import com.portal.jobportal.entity.Application;
import com.portal.jobportal.entity.Job;
import com.portal.jobportal.entity.User;
import com.portal.jobportal.repository.ApplicationRepository;
import com.portal.jobportal.repository.JobRepository;
import com.portal.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<?> applyJob(@PathVariable Long jobId, Authentication auth) {

        String email = auth.getName();
        User applicant = userRepository.findByEmail(email).orElseThrow();

        if(applicant.getRole().equals("EMPLOYER")) {
            return ResponseEntity.status(403).body("Employers cannot apply for jobs!");
        }

        if (applicationRepository.existsByApplicantIdAndJobId(applicant.getId(), jobId)) {
            return ResponseEntity.badRequest().body("You have already applied for this job!");
        }

        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        Application application = new Application();
        application.setApplicant(applicant);
        application.setJob(job);

        applicationRepository.save(application);

        return ResponseEntity.ok("Applied Successfully!");
    }

    @GetMapping("/my-applications")
    public ResponseEntity<List<Application>> getMyApplications(Authentication auth) {
        String email = auth.getName();
        User applicant = userRepository.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(applicationRepository.findByApplicantId(applicant.getId()));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getApplicantsForJob(@PathVariable Long jobId, Authentication auth) {
        return ResponseEntity.ok(applicationRepository.findByJobId(jobId));
    }
}