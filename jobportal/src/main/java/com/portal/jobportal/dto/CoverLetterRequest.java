package com.portal.jobportal.dto;

import lombok.Data;

@Data
public class CoverLetterRequest {
    private String userName;
    private String companyName;
    private String jobTitle;
    private String jobDescription;
    private String userSkills;
}