package com.risingtest.wanted.src.follow;

import com.risingtest.wanted.src.follow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Follow> findByCompanyIdAndUserId(long id, long id1);
}
