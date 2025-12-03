package com.portal.jobportal.service;

import com.portal.jobportal.dto.JobRequest;
import com.portal.jobportal.entity.Job;
import com.portal.jobportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Create new job
    public Job createJob(JobRequest request, Long employerId) {

        Job job = Job.builder()
                .title(request.getTitle())
                .company(request.getCompany())
                .location(request.getLocation())
                .jobType(request.getJobType())
                .category(request.getCategory())
                .description(request.getDescription())
                .salaryMin(request.getSalaryMin())
                .salaryMax(request.getSalaryMax())
                .employerId(employerId)
                .build();

        return jobRepository.save(job);
    }


    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }


    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }


    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
