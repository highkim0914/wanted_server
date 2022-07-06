package com.risingtest.wanted.src.award;

import com.risingtest.wanted.src.award.model.Award;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardRepository extends JpaRepository<Award, Long> {
}
