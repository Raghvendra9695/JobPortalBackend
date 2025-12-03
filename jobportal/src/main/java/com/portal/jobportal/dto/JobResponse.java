package com.portal.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobResponse {
    private Long id;
    private String title;
    private String company;
    private String location;
    private String jobType;
    private String category;
    private String description;
    private int salaryMin;
    private int salaryMax;
}
