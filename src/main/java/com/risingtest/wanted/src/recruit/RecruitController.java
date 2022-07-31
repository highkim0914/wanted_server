package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.bookmark.model.BasicBookmark;
import com.risingtest.wanted.src.company.CompanyProvider;
import com.risingtest.wanted.src.jobapplication.model.JobApplicationFormReq;
import com.risingtest.wanted.src.jobapplication.model.PostJobApplicationReq;
import com.risingtest.wanted.src.likemark.model.BasicLikemark;
import com.risingtest.wanted.src.recruit.model.GetRecruitRes;
import com.risingtest.wanted.src.recruit.model.GetRecruitsReq;
import com.risingtest.wanted.src.recruit.model.RecruitsAndBookmarksRes;
import com.risingtest.wanted.utils.ValidationRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruits")
public class RecruitController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

//    private RecruitProvider recruitProvider;
//    private HashtagProvider hashtagProvider;
//    private TechstackProvider techstackProvider;
//
//    @Autowired
//    public RecruitController(RecruitProvider recruitProvider, HashtagProvider hashtagProvider, TechstackProvider techstackProvider) {
//        this.recruitProvider = recruitProvider;
//        this.hashtagProvider = hashtagProvider;
//        this.techstackProvider = techstackProvider;
//    }

    private RecruitProvider recruitProvider;

    private RecruitService recruitService;

    private CompanyProvider companyProvider;

    @Autowired
    public RecruitController(RecruitProvider recruitProvider, RecruitService recruitService, CompanyProvider companyProvider) {
        this.recruitProvider = recruitProvider;
        this.recruitService = recruitService;
        this.companyProvider = companyProvider;
    }

    @GetMapping
    public BaseResponse<RecruitsAndBookmarksRes> getRecruitsWithFilter(GetRecruitsReq getRecruitsReq){
        logger.info("getRecruitsWithFilter : " + getRecruitsReq.toString());

        if(getRecruitsReq.getYears().size()>2){
            return new BaseResponse<>(BaseResponseStatus.GET_RECRUIT_TOO_MANY_YEARS);
        }
        try {
            RecruitsAndBookmarksRes recruitsAndBookmarksRes = recruitProvider.getRecruitsWithFilter(getRecruitsReq);
            return new BaseResponse<>(recruitsAndBookmarksRes);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{id}/application")
    public BaseResponse<JobApplicationFormReq> requestJobApplyForm(@PathVariable long id){
        logger.info("requestJobApplyForm : " + id);
        try {
            JobApplicationFormReq jobApplicationFormReq = recruitService.getJobApplicationReq();
            return new BaseResponse<>(jobApplicationFormReq);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/{id}/application")
    public BaseResponse<BaseResponseStatus> createJobApplyWithRecruitId(@PathVariable long id, @RequestBody PostJobApplicationReq postJobApplicationReq){
        logger.info("createJobApplyWithRecruitId : " + id + " " + postJobApplicationReq);
        if(!ValidationRegex.isRegexPhoneNumber(postJobApplicationReq.getPhoneNumber())){
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PHONE_NUMBER);
        }
        try {
            recruitService.createJobApplyWithRecruitId(postJobApplicationReq,id);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{id}")
    public BaseResponse<GetRecruitRes> getRecruitById(@PathVariable long id){
        logger.info("getRecruitById : " + id);
        try {
            GetRecruitRes getRecruitRes = recruitProvider.getRecruitRes(id);

            return new BaseResponse<>(getRecruitRes);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/{recruitId}/bookmarks")
    public BaseResponse<BasicBookmark> toggleBookmark(@PathVariable long recruitId){
        logger.info("toggleBookmark : " + recruitId);
        try {
            BasicBookmark basicBookmark = recruitService.toggleBookmark(recruitId);
            return new BaseResponse<>(basicBookmark);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/{recruitId}/likemarks")
    public BaseResponse<BasicLikemark> toggleLikemark(@PathVariable long recruitId){
        logger.info("toggleLikemark : " + recruitId);
        try {
            BasicLikemark basicLikeMark = recruitService.toggleLikemark(recruitId);
            return new BaseResponse<>(basicLikeMark);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
