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
@CrossOrigin(origins = "https://job-portal-frontend-7bxv8dnjw.vercel.app/")
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
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        if (!user.getRole().equals("EMPLOYER")) {
            return ResponseEntity.status(403).body("Only employers can post jobs!");
        }

        Job job = jobService.createJob(request, user.getId());
        return ResponseEntity.ok(job);
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