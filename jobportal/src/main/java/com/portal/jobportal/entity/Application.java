package com.portal.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User applicant;
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    private String status = "APPLIED";

    private LocalDateTime appliedAt = LocalDateTime.now();
}