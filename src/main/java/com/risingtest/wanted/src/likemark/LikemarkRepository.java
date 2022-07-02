package com.risingtest.wanted.src.likemark;

import com.risingtest.wanted.src.likemark.model.Likemark;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface LikemarkRepository extends JpaRepository<Likemark,Long> {
    //@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="3000")})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Likemark> findByRecruitIdAndUserId(long recruitId, long userId);

    List<Likemark> findAllByIdAndStatus(long id, int status);

    List<Likemark> findAllByUserId(long userId);
}
