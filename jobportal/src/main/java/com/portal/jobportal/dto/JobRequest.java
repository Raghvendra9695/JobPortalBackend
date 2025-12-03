package com.portal.jobportal.dto;

import lombok.Data;

@Data
public class JobRequest {
    private String title;
    private String company;
    private String location;
    private String jobType;
    private String category;
    private String description;
    private int salaryMin;
    private int salaryMax;
}
