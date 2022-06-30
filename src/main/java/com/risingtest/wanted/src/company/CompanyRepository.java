package com.risingtest.wanted.src.company;

import com.risingtest.wanted.src.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
