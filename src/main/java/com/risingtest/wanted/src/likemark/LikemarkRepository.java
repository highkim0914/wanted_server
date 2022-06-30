package com.risingtest.wanted.src.likemark;

import com.risingtest.wanted.src.likemark.model.Likemark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikemarkRepository extends JpaRepository<Likemark,Long> {
    Optional<Likemark> findByRecruitIdAndUserId(long recruitId, long userId);

    List<Likemark> findAllByIdAndStatus(long id, int status);
}
