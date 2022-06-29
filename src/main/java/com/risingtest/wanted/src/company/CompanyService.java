package com.risingtest.wanted.src.company;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.recruit.PostRecruitReq;
import com.risingtest.wanted.src.recruit.RecruitRepository;
import com.risingtest.wanted.src.recruit.model.Recruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RecruitRepository recruitRepository;

    public Company createCompany(PostCompanyReq postCompanyReq) throws BaseException {
        try {
            Company company = companyRepository.save(postCompanyReq.toEntity());
            return company;
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public Recruit createRecruit(PostRecruitReq postRecruitReq, Company company) {
        try {
            Recruit recruit = postRecruitReq.toEntity(company);
            recruitRepository.save(recruit);
            return recruit;
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
