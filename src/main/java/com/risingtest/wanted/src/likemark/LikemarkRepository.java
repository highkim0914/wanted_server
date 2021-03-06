package com.risingtest.wanted.src.likemark;

import com.risingtest.wanted.src.likemark.model.Likemark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface LikemarkRepository extends JpaRepository<Likemark,Long> {
    //@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="3000")})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Likemark> findByRecruitIdAndUserId(long recruitId, long userId);

    List<Likemark> findAllByIdAndStatus(long id, int status);

    List<Likemark> findAllByUserIdAndStatus(long userId, int i);

    @Query(value = "select l.recruit_id from likemark l where l.user_id = ?1 and l.status = ?2",nativeQuery = true)
    List<Long> findRecruitIdsByUserIdAndStatus(long userIdx, int i);
}
