package com.portal.jobportal.controller;

import com.portal.jobportal.dto.JobRequest;
import com.portal.jobportal.entity.Job;
import com.portal.jobportal.entity.User;
import com.portal.jobportal.repository.UserRepository;
import com.portal.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createJob(
            @RequestBody JobRequest request,
            Authentication auth
    ) {
        // 1. Current Logged-in User nikalo
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. ✅ FIX: Role check me RECRUITER ko bhi add kiya
        String role = user.getRole().toUpperCase(); // Case insensitive check

        if (!role.equals("EMPLOYER") && !role.equals("RECRUITER")) {
            return ResponseEntity.status(403).body("Access Denied! Only Employers or Recruiters can post jobs.");
        }

        // 3. Job Create karo
        try {
            Job job = jobService.createJob(request, user.getId());
            return ResponseEntity.ok(job);
        } catch (Exception e) {
            e.printStackTrace(); // Console me error dikhega agar Service fail hua
            return ResponseEntity.status(500).body("Error creating job: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAll() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJob(@PathVariable Long id) {
        Job job = jobService.getJobById(id);

        if (job == null)
            return ResponseEntity.status(404).body("Job not found");

        return ResponseEntity.ok(job);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok("Job deleted");
    }
}