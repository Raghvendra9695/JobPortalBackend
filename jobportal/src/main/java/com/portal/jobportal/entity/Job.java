package com.portal.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jobs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String location;
    private String jobType;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int salaryMin;
    private int salaryMax;

    private Long employerId;
}
