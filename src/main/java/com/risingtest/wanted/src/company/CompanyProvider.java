package com.risingtest.wanted.src.company;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.company.model.BasicCompany;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.follow.FollowProvider;
import com.risingtest.wanted.src.user.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyProvider {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private FollowProvider followProvider;

    public Company findById(long id) throws BaseException {
        return companyRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_COMPANY));
    }

    public List<BasicCompany> findCompanyWithFollows() throws BaseException{
        List<Company> companies = companyRepository.findAllByIdIn(followProvider.findCompanyIdsByUserToken());
        if(companies.isEmpty()){
            return new ArrayList<>();
        }
        return companies.stream()
                .map(BasicCompany::from)
                .collect(Collectors.toList());
    }
}
