package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.src.recruit.model.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> , JpaSpecificationExecutor<Recruit> {
    List<Recruit> findAllByIdIn(List<Long> ids);
}
