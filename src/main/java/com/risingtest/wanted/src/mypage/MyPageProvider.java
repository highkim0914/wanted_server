package com.risingtest.wanted.src.mypage;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.src.recruit.RecruitProvider;
import com.risingtest.wanted.src.resume.ResumeProvider;
import com.risingtest.wanted.src.resume.model.BasicResume;
import com.risingtest.wanted.src.user.UserProvider;
import com.risingtest.wanted.src.user.UserService;
import com.risingtest.wanted.src.user.model.GetMyPageRes;
import com.risingtest.wanted.src.user.model.ProfilesOfUser;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyPageProvider {
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private RecruitProvider recruitProvider;
    @Autowired
    private UserService userService;

    @Autowired
    private ResumeProvider resumeProvider;


    public GetMyPageRes getMyPage() throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        GetMyPageRes getMyPageRes = new GetMyPageRes();
        getMyPageRes.setEmail(user.getEmail());
        getMyPageRes.setName(user.getUserName());
        getMyPageRes.setPhoneNumber(user.getPhoneNumber());
        getMyPageRes.setJobApplications(userProvider.findBasicJobApplicationsWithUserToken());
        getMyPageRes.setBookmarks(recruitProvider.findRecruitWithBookmarks());
        getMyPageRes.setLikemarks(recruitProvider.findRecruitWithLikemarks());
        return getMyPageRes;
    }

    public ProfilesOfUser getUserProfiles() throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        ProfilesOfUser profilesOfUser = ProfilesOfUser.from(user);
        List<BasicResume> resumes = resumeProvider.findAllWithUserToken().stream()
                .map(BasicResume::from)
                .collect(Collectors.toList());
        profilesOfUser.setResumes(resumes);
        return profilesOfUser;
    }
}
