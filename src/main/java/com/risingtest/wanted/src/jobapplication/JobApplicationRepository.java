package com.risingtest.wanted.src.jobapplication;

import com.risingtest.wanted.src.jobapplication.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findAllByRecruitId(long recruitId);
}
