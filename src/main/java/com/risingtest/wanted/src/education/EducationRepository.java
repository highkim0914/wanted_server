package com.risingtest.wanted.src.education;

import com.risingtest.wanted.src.education.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
