package com.risingtest.wanted.src.company;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.recruit.PostRecruitReq;
import com.risingtest.wanted.src.recruit.PostRecruitRes;
import com.risingtest.wanted.src.recruit.model.BasicRecruitRes;
import com.risingtest.wanted.src.recruit.model.GetRecruitRes;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.user.model.User;
import com.risingtest.wanted.utils.ValidationRegex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyProvider companyProvider;

    @PostMapping()
    public BaseResponse<BasicCompany> createCompany(@RequestBody PostCompanyReq postCompanyReq){
        if(!ValidationRegex.isRegexPhoneNumber(postCompanyReq.getContactNumber())){
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
        try {
            Company company = companyProvider.findById(postRecruitReq.getCompanyId());
            Recruit recruit = companyService.createRecruit(postRecruitReq, company);
            return new BaseResponse<>(new PostRecruitRes(recruit.getId()));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


}
