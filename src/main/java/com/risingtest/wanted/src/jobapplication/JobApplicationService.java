package com.risingtest.wanted.src.jobapplication;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.jobapplication.model.ApplicationStatus;
import com.risingtest.wanted.src.jobapplication.model.JobApplication;
import com.risingtest.wanted.src.jobapplication.model.JobApplicationFormReq;
import com.risingtest.wanted.src.jobapplication.model.PostJobApplicationReq;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.resume.model.BasicResume;
import com.risingtest.wanted.src.resume.model.Resume;
import com.risingtest.wanted.src.user.UserProvider;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {
    //

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserProvider userProvider;

    public JobApplication createJobApplication(PostJobApplicationReq postJobApplicationReq, User user, Resume resume, Recruit recruit) throws BaseException{
        try {
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
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public JobApplicationFormReq getJobApplicationReq() throws BaseException {
        User user = userProvider.findUserWithUserJwtToken();
        try {
            List<Resume> resumes = user.getResumes();
            List<BasicResume> basicResumes = resumes.stream()
                    .map(BasicResume::from)
                    .collect(Collectors.toList());
            JobApplicationFormReq jobApplicationFormReq = JobApplicationFormReq.builder()
                    .name(user.getUserName())
                    .email(user.getEmail())
                    .phoneNumber("")
                    .recommender("")
                    .basicResumes(basicResumes)
                    .build();
            return jobApplicationFormReq;
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
