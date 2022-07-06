package com.risingtest.wanted.src.result;

import com.risingtest.wanted.src.result.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
