package com.risingtest.wanted.src.company;

import com.risingtest.wanted.src.company.model.BasicCompany;
import com.risingtest.wanted.src.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByIdIn(List<Long> companyIdsByUserToken);
}
