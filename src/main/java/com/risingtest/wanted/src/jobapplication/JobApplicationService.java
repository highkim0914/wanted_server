package com.risingtest.wanted.src.jobapplication;

import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.resume.model.Resume;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationService {
    //

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public JobApplication createJobApplication(PostJobApplicationReq postJobApplicationReq, User user, Resume resume, Recruit recruit) {
        JobApplication jobApplication = JobApplication.builder()
                .name(postJobApplicationReq.getName())
                .phoneNumber(postJobApplicationReq.getPhoneNumber())
                .email(postJobApplicationReq.getEmail())
                .recommender(postJobApplicationReq.getRecommender())
                .resume(resume)
                .recruit(recruit)
                .applicationStatus(ApplicationStatus.APPLIED)
                .user(user)
                .recruit(recruit)
                .build();
        return jobApplicationRepository.save(jobApplication);
    }
}
