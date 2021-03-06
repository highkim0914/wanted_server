package com.risingtest.wanted.src.company;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.company.model.*;
import com.risingtest.wanted.src.jobapplication.model.BasicJobApplication;
import com.risingtest.wanted.src.recruit.RecruitService;
import com.risingtest.wanted.src.recruit.model.PostRecruitReq;
import com.risingtest.wanted.src.recruit.model.PostRecruitRes;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.utils.ValidationRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/businesses")
public class BusinessController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyProvider companyProvider;

    @Autowired
    private RecruitService recruitService;

    @PostMapping()
    public BaseResponse<BasicCompany> createCompany(@RequestBody PostCompanyReq postCompanyReq){
        logger.info("createCompany: {}", postCompanyReq);
        if(!ValidationRegex.isRegexContactNumber(postCompanyReq.getContactNumber())){
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PHONE_NUMBER);
        }
        if(!ValidationRegex.isRegexEmail(postCompanyReq.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
        }
        if(postCompanyReq.getEmployeesNumber()<10){
            return new BaseResponse<>(BaseResponseStatus.INVALID_EMPLOYEE_NUMBER);
        }
        if(postCompanyReq.getEstablishmentYear() > LocalDate.now().getYear()){
            return new BaseResponse<>(BaseResponseStatus.INVALID_ESTABLISHMENT_YEAR);
        }

        if(postCompanyReq.getRegistrationNumber().length()!=10){
            return new BaseResponse<>(BaseResponseStatus.INVALID_REGISTRATION_NUMBER);
        }
        try {
            Company company = companyService.createCompany(postCompanyReq);
            return new BaseResponse<>(BasicCompany.from(company));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping()
    public BaseResponse<BasicCompany> updateCompany(@RequestBody PostCompanyReq postCompanyReq){
        logger.info("updateCompany: {}", postCompanyReq);
        if(!ValidationRegex.isRegexContactNumber(postCompanyReq.getContactNumber())){
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PHONE_NUMBER);
        }
        if(!ValidationRegex.isRegexEmail(postCompanyReq.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
        }
        if(postCompanyReq.getEmployeesNumber()<10){
            return new BaseResponse<>(BaseResponseStatus.INVALID_EMPLOYEE_NUMBER);
        }
        if(postCompanyReq.getEstablishmentYear() > LocalDate.now().getYear()){
            return new BaseResponse<>(BaseResponseStatus.INVALID_ESTABLISHMENT_YEAR);
        }
        if(postCompanyReq.getRegistrationNumber().length()!=10){
            return new BaseResponse<>(BaseResponseStatus.INVALID_REGISTRATION_NUMBER);
        }
        try {
            Company company = companyService.updateCompany(postCompanyReq);
            return new BaseResponse<>(BasicCompany.from(company));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

//    @GetMapping("/{id}")
//    public BaseResponse<GetCompanyRes> getCompany(@PathVariable long id){
//        logger.info("getCompany: {}", id);
//        try {
//            Company company = companyProvider.findById(id);
//            return new BaseResponse<>(GetCompanyRes.from(company));
//        }
//        catch (BaseException e){
//            return new BaseResponse<>(e.getStatus());
//        }
//    }

    @PostMapping("/recruits")
    public BaseResponse<PostRecruitRes> createRecruit(@RequestBody PostRecruitReq postRecruitReq){
        logger.info("createRecruit: {}", postRecruitReq);
        try {
            Company company = companyProvider.findById(postRecruitReq.getCompanyId());
            Recruit recruit = recruitService.createRecruit(postRecruitReq, company);
            return new BaseResponse<>(new PostRecruitRes(recruit.getId()));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/recruits/{recruitId}")
    public BaseResponse<PostRecruitRes> updateRecruit(@PathVariable long recruitId, @RequestBody PostRecruitReq postRecruitReq){
        logger.info("createRecruit: {}", postRecruitReq);
        try {
            Company company = companyProvider.findById(postRecruitReq.getCompanyId());
            Recruit recruit = recruitService.updateRecruit(postRecruitReq, company, recruitId);
            return new BaseResponse<>(new PostRecruitRes(recruit.getId()));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/recruits/{recruitId}")
    public BaseResponse<BaseResponseStatus> deleteRecruit(@PathVariable long recruitId){
        logger.info("delete recruit");
        try {
            recruitService.deleteRecruit(recruitId);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/images")
    public BaseResponse<CompanyPhotoRes> uploadCompanyImages(@RequestPart List<MultipartFile> images){
        logger.info("uploadCompanyImages: {}", images.stream().map(MultipartFile::getName).collect(Collectors.toList()));
        try {
            //id = jwtService.getUserIdx();
            String photoUrl = companyService.uploadCompanyImagesAndSetPhotoUrl(images);
            List<String> photoUrls = Arrays.stream(photoUrl.split(",")).collect(Collectors.toList());
            return new BaseResponse<>(new CompanyPhotoRes(photoUrls));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/profile-images")
    public BaseResponse<String> uploadCompanyProfileImages(@RequestPart MultipartFile images){
        logger.info("uploadCompanyProfileImages: {}", images.getName());
        try {
            String photoUrl = companyService.uploadCompanyProfileImageAndProfilePhotoUrl(images);
            return new BaseResponse<>(photoUrl);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

//    @PostMapping("/{companyId}/follows")
//    public BaseResponse<BasicFollow> toggleFollow(@PathVariable long companyId){
//        logger.info("toggleFollow: {}", companyId);
//        try {
//            BasicFollow basicFollow = companyService.toggleFollow(companyId);
//            return new BaseResponse<>(basicFollow);
//        }
//        catch (BaseException e){
//            return new BaseResponse<>(e.getStatus());
//        }
//    }

    @GetMapping("/recruits/{recruitId}/job-applications")
    public BaseResponse<List<BasicJobApplication>> getJobApplicationsOfRecruit(@PathVariable long recruitId){
        logger.info("getJobApplicationsOfRecruit: {}", recruitId);
        try {
            List<BasicJobApplication> applications = companyProvider.getJobApplicationsOfRecruit(recruitId);
            return new BaseResponse<>(applications);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
