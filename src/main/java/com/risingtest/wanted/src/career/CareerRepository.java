package com.risingtest.wanted.src.career;

import com.risingtest.wanted.src.career.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
}
