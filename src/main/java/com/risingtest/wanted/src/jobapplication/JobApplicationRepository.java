package com.risingtest.wanted.src.jobapplication;

import com.risingtest.wanted.src.jobapplication.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
}
