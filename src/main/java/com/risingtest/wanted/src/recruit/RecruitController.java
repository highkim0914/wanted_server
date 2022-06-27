package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.hashtag.BasicHashtag;
import com.risingtest.wanted.src.hashtag.HashtagProvider;
import com.risingtest.wanted.src.recruit.dto.BasicRecruitRes;
import com.risingtest.wanted.src.recruit.dto.GetRecruitRes;
import com.risingtest.wanted.src.techstack.BasicTechstack;
import com.risingtest.wanted.src.techstack.TechstackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/recruits")
public class RecruitController {

    private RecruitProvider recruitProvider;
    private HashtagProvider hashtagProvider;
    private TechstackProvider techstackProvider;

    @Autowired
    public RecruitController(RecruitProvider recruitProvider, HashtagProvider hashtagProvider, TechstackProvider techstackProvider) {
        this.recruitProvider = recruitProvider;
        this.hashtagProvider = hashtagProvider;
        this.techstackProvider = techstackProvider;
    }

    @GetMapping
    public BaseResponse<GetRecruitRes> getRecruitsWithFilter(@RequestParam(name = "job_group", defaultValue = "") String jobGroup,
                                                             @RequestParam(name = "years", defaultValue = "0") List<Integer> years,
                                                             @RequestParam(name = "positions", defaultValue = "") List<String> positions,
                                                             @RequestParam(name = "locations", defaultValue = "") List<String> locations,
                                                             @RequestParam(name = "hashtags", defaultValue = "") List<Long> hashtags,
                                                             @RequestParam(name = "techstacks", defaultValue = "") List<Long> techstacks,
                                                             @RequestParam(name = "init" ,defaultValue = "false") Boolean hasData
    )
    {
        if(years.size()>2){
            return new BaseResponse<>(BaseResponseStatus.REQUEST_ERROR);
        }
        List<BasicRecruitRes> list = recruitProvider.getRecruitsWithFilter(jobGroup,years,  positions, locations, hashtags, techstacks);
        if(!hasData){
            List<BasicHashtag> list1 = hashtagProvider.findAll();
            List<BasicTechstack> list2 = techstackProvider.findAll();
            return new BaseResponse<>(new GetRecruitRes(list,list1,list2));
        }
        return new BaseResponse<>(new GetRecruitRes(list, Collections.emptyList(),Collections.emptyList()));
    }

    @GetMapping("/{id}")
    public BaseResponse<GetRecruitRes> getRecruitById(@PathVariable long id){
        try {
            List<BasicRecruitRes> list = recruitProvider.getRecruitById(id);
            List<BasicHashtag> list1 = hashtagProvider.findAll();
            List<BasicTechstack> list2 = techstackProvider.findAll();
            return new BaseResponse<>(new GetRecruitRes(list,list1, list2));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
