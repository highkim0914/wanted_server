package com.risingtest.wanted.src.follow;

import com.risingtest.wanted.src.follow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Follow> findByCompanyIdAndUserId(long id, long id1);

    List<Follow> findAllByUserId(long userId);
    @Query(value = "select f.company_id from follow f where f.user_id = ?1 and f.status = ?2",nativeQuery = true)
    List<Long> findCompanyIdsByUserIdAndStatus(long userIdx, int i);
}
