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


    public Job createJob(JobRequest request, Long employerId) {
        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setCategory(request.getCategory());

        job.setDescription(request.getDescription());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());

        job.setEmployerId(employerId);

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