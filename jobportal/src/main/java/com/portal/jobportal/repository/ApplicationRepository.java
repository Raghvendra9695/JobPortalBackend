package com.portal.jobportal.repository;

import com.portal.jobportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByApplicantIdAndJobId(Long userId, Long jobId);
    List<Application> findByJobId(Long jobId);

    List<Application> findByApplicantId(Long userId);
}