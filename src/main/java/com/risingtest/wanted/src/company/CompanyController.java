package com.risingtest.wanted.src.company;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.company.model.GetCompanyRes;
import com.risingtest.wanted.src.follow.model.BasicFollow;
import com.risingtest.wanted.src.recruit.RecruitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyProvider companyProvider;

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
