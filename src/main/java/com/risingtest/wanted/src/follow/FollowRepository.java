package com.risingtest.wanted.src.follow;

import com.risingtest.wanted.src.follow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    Optional<Follow> findByCompanyIdAndUserId(long id, long id1);
}
