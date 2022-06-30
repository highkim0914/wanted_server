package com.risingtest.wanted.src.company;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.company.model.BasicCompany;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.company.model.GetCompanyRes;
import com.risingtest.wanted.src.company.model.PostCompanyReq;
import com.risingtest.wanted.src.follow.model.BasicFollow;
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

@RestController
@RequestMapping("/companies")
public class CompanyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyProvider companyProvider;

    @PostMapping()
    public BaseResponse<BasicCompany> createCompany(@RequestBody PostCompanyReq postCompanyReq){
        logger.info("회사 생성");
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

    @GetMapping("/{id}")
    public BaseResponse<GetCompanyRes> getCompany(@PathVariable long id){
        logger.info("회사 조회");
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
        logger.info("회사 채용공고 생성");
        try {
            Company company = companyProvider.findById(postRecruitReq.getCompanyId());
            Recruit recruit = companyService.createRecruit(postRecruitReq, company);
            return new BaseResponse<>(new PostRecruitRes(recruit.getId()));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/photos")
    public BaseResponse<String> uploadCompanyImages(@RequestPart List<MultipartFile> images){
        logger.info("회사 공개용 이미지 업로드");
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
        logger.info("회사 프로필 이미지 업로드");
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
        logger.info("팔로우 생성/해제");
        try {
            BasicFollow basicFollow = companyService.toggleFollow(companyId);
            return new BaseResponse<>(basicFollow);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
