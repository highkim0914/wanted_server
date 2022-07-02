package com.risingtest.wanted.src.company;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.company.model.BasicCompany;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.company.model.GetCompanyRes;
import com.risingtest.wanted.src.company.model.PostCompanyReq;
import com.risingtest.wanted.src.follow.model.BasicFollow;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
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

    @GetMapping("/{id}")
    public BaseResponse<GetCompanyRes> getCompany(@PathVariable long id){
        logger.info("getCompany: {}", id);
        try {
            Company company = companyProvider.findById(id);
            return new BaseResponse<>(GetCompanyRes.from(company));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

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

    @PostMapping("/photos")
    public BaseResponse<String> uploadCompanyImages(@RequestPart List<MultipartFile> images){
        logger.info("uploadCompanyImages: {}", images.stream().map(MultipartFile::getName).collect(Collectors.toList()));
        try {
            long id = 1;
            //id = jwtService.getUserIdx();
            String photoUrl = companyService.uploadCompanyImagesAndSetPhotoUrl(id, images);
            return new BaseResponse<>(photoUrl);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/profile-photos")
    public BaseResponse<String> uploadCompanyProfileImages(@RequestPart MultipartFile image){
        logger.info("uploadCompanyProfileImages: {}", image.getName());
        try {
            long id = 1;
            //id = jwtService.getUserIdx();
            String photoUrl = companyService.uploadCompanyProfileImageAndProfilePhotoUrl(id, image);
            return new BaseResponse<>(photoUrl);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/{companyId}/follows")
    public BaseResponse<BasicFollow> toggleFollow(@PathVariable long companyId){
        logger.info("toggleFollow: {}", companyId);
        try {
            BasicFollow basicFollow = companyService.toggleFollow(companyId);
            return new BaseResponse<>(basicFollow);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
