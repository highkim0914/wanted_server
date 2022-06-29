package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.bookmark.BasicBookmark;
import com.risingtest.wanted.src.company.Company;
import com.risingtest.wanted.src.company.CompanyProvider;
import com.risingtest.wanted.src.hashtag.CompanyHashtag;
import com.risingtest.wanted.src.recruit.model.BasicRecruitRes;
import com.risingtest.wanted.src.recruit.model.GetRecruitRes;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.techstack.CompanyTechstack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruits")
public class RecruitController {

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
    public BaseResponse<List<BasicRecruitRes>> getRecruitsWithFilter(@RequestParam(name = "job_group", defaultValue = "") String jobGroup,
                                                             @RequestParam(name = "years", defaultValue = "0") List<Integer> years,
                                                             @RequestParam(name = "positions", defaultValue = "") List<String> positions,
                                                             @RequestParam(name = "locations", defaultValue = "") List<String> locations,
                                                             @RequestParam(name = "hashtags", defaultValue = "") List<Long> hashtags,
                                                             @RequestParam(name = "techstacks", defaultValue = "") List<Long> techstacks
    ){
        if(years.size()>2){
            return new BaseResponse<>(BaseResponseStatus.GET_RECRUIT_TOO_MANY_YEARS);
        }
        List<BasicRecruitRes> list = recruitProvider.getRecruitsWithFilter(jobGroup, years,  positions, locations, hashtags, techstacks);
        return new BaseResponse<>(list);
    }

    @GetMapping("/{id}")
    public BaseResponse<GetRecruitRes> getRecruitById(@PathVariable long id){
        try {
            Recruit recruit = recruitProvider.getRecruitById(id);
            Company company = recruit.getCompany();
            return new BaseResponse<>(GetRecruitRes.from(recruit,company));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/{recruitId}/bookmarks")
    public BaseResponse<BasicBookmark> toggleBookmark(@PathVariable long recruitId){
        try {
            BasicBookmark basicBookmark = recruitService.toggleBookmark(recruitId);
            return new BaseResponse<>(basicBookmark);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
