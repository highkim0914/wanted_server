package com.risingtest.wanted.src.resume;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.resume.model.BasicResume;
import com.risingtest.wanted.src.resume.model.Resume;
import com.risingtest.wanted.src.resume.model.ResumeDto;
import com.risingtest.wanted.utils.ValidationRegex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    // 이력서 리스트 가져오기
    // 이력서 생성 -> 기본 이력서 줌
    // 수정 후 저장 가능
    // 임시 저장 가능함
    // 이름만 바꾸기 가능
    // 삭제 기능
    //

    @Autowired
    ResumeService resumeService;

    @Autowired
    ResumeProvider resumeProvider;

    @GetMapping("")
    public BaseResponse<List<BasicResume>> getResumes(){
        try {
            List<Resume> resumes = resumeProvider.findAllWithUserToken();
            List<BasicResume> basicResumes = resumes.stream()
                    .map(BasicResume::from)
                    .collect(Collectors.toList());
            return new BaseResponse<>(basicResumes);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("")
    public BaseResponse<ResumeDto> createResume(){
        try {
            Resume resume = resumeService.createResume();
            return new BaseResponse<>(ResumeDto.from(resume));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{resumeId}")
    public BaseResponse<ResumeDto> getResumeById(@PathVariable long resumeId){
        try {
            Resume resume = resumeProvider.findById(resumeId);
            return new BaseResponse<>(ResumeDto.from(resume));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/{resumeId}")
    public BaseResponse<BaseResponseStatus> deleteResume(@PathVariable long resumeId){
        try {
            resumeService.deleteResume(resumeId);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/{resumeId}")
    public BaseResponse<BaseResponseStatus> updateResume(@RequestBody ResumeDto resumeDto,
                                                         @PathVariable long resumeId,
                                                         @RequestParam(value = "permanent", defaultValue = "false") boolean isPermanent){
        if(resumeDto.getIntroduction().length()<400 && isPermanent){
            return new BaseResponse<>(BaseResponseStatus.INVALID_INTRODUCTION);
        }
        if(!ValidationRegex.isRegexPhoneNumber(resumeDto.getPhoneNumber())){
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PHONE_NUMBER);
        }
        if(resumeDto.getId()!=resumeId){
            return new BaseResponse<>(BaseResponseStatus.REQUEST_ERROR);
        }
        try {

            Resume resume = resumeService.updateResume(resumeDto, isPermanent);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
