package com.risingtest.wanted.src.jobapplication;

import com.risingtest.wanted.src.jobapplication.model.BasicJobApplication;
import com.risingtest.wanted.src.jobapplication.model.JobApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationProvider {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public List<JobApplication> findApplicationsWithRecruit(long recruitId){
        List<JobApplication> applications = jobApplicationRepository.findAllByRecruitId(recruitId);

        return applications;
    }

}
