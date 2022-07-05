package com.risingtest.wanted.src.user;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.bookmark.BasicBookmark;
import com.risingtest.wanted.src.company.CompanyProvider;
import com.risingtest.wanted.src.company.model.BasicCompany;
import com.risingtest.wanted.src.follow.model.BasicFollow;
import com.risingtest.wanted.src.likemark.model.BasicLikemark;
import com.risingtest.wanted.src.recruit.RecruitProvider;
import com.risingtest.wanted.src.recruit.model.BasicRecruitRes;
import com.risingtest.wanted.src.resume.ResumeProvider;
import com.risingtest.wanted.src.resume.model.BasicResume;
import com.risingtest.wanted.src.user.model.GetMyPageRes;
import com.risingtest.wanted.src.user.model.PostUserReq;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mypages")
public class MyPageController {
    @Autowired
    private MyPageService myPageService;

    @Autowired
    private MyPageProvider myPageProvider;

    @Autowired
    private RecruitProvider recruitProvider;

    @Autowired
    private CompanyProvider companyProvider;

    @Autowired
    private ResumeProvider resumeProvider;

    @GetMapping
    public BaseResponse<GetMyPageRes> getMyPage(){
        try {
            GetMyPageRes getMyPageRes = myPageProvider.getMyPage();
            return new BaseResponse<>(getMyPageRes);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/likemarks")
    public BaseResponse<List<BasicRecruitRes>> getLikemarks(){
        try {
            List<BasicRecruitRes> basicRecruitRes = recruitProvider.findRecruitWithLikemarks();
            return new BaseResponse<>(basicRecruitRes);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/bookmarks")
    public BaseResponse<List<BasicRecruitRes>> getBookmarks(){
        try {
            List<BasicRecruitRes> basicRecruitRes = recruitProvider.findRecruitWithBookmarks();
            return new BaseResponse<>(basicRecruitRes);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/follows")
    public BaseResponse<List<BasicCompany>> getFollows(){
        try {
            List<BasicCompany> companies = companyProvider.findCompanyWithFollows();
            return new BaseResponse<>(companies);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/infos")
    public BaseResponse<BaseResponseStatus> patchUserInfo(@RequestBody PostUserReq postUserReq){
        try {
            myPageService.patchUserInfo(postUserReq);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/profiles")
    public BaseResponse<ProfilesOfUser> getProfile(){
        try {
            ProfilesOfUser profilesOfUser = myPageProvider.getUserProfiles();

            return new BaseResponse<>(profilesOfUser);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/profiles")
    public BaseResponse<BaseResponseStatus> patchProfile(@RequestBody ProfilesOfUser profilesOfUser){
        try {
            myPageService.patchUserProfiles(profilesOfUser);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
