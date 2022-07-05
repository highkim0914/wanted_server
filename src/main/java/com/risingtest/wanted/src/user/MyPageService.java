package com.risingtest.wanted.src.user;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.src.jobapplication.model.BasicJobApplication;
import com.risingtest.wanted.src.recruit.RecruitProvider;
import com.risingtest.wanted.src.user.model.GetMyPageRes;
import com.risingtest.wanted.src.user.model.PostUserReq;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyPageService {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private RecruitProvider recruitProvider;
    @Autowired
    private UserService userService;

    @Transactional
    public void patchUserInfo(PostUserReq postUserReq) throws BaseException {
        User user = userProvider.findUserWithUserJwtToken();
        user.setUserName(postUserReq.getUserName());
        user.setEmail(postUserReq.getEmail());
        user.setPhoneNumber(postUserReq.getPhoneNumber());
        userService.saveUser(user);
    }

    @Transactional
    public void patchUserProfiles(ProfilesOfUser profilesOfUser) throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        user.setProfilesResumeId(profilesOfUser.getProfilesResumeId());
        user.setJobGroup(profilesOfUser.getJobGroup());
        user.setPositions(String.join(",",profilesOfUser.getPositions()));
        user.setLookingForJob(profilesOfUser.getLookingForJob());
        user.setCareer(profilesOfUser.getCareer());
        userService.saveUser(user);
    }

    public List<BasicJobApplication> findBasicJobApplicationsWithUserToken() throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        List<BasicJobApplication> list = user.getJobApplications().stream()
                .filter(jobApplication -> jobApplication.getStatus()==0)
                .map(BasicJobApplication::from)
                .collect(Collectors.toList());
        return list;
    }
}
