package com.risingtest.wanted.src.company;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.hashtag.CompanyHashtag;
import com.risingtest.wanted.src.techstack.CompanyTechstack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyProvider {

    @Autowired
    CompanyRepository companyRepository;

    public Company findById(long id) throws BaseException {
        return companyRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_COMPANY));
    }

}
