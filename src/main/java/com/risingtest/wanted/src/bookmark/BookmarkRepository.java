package com.risingtest.wanted.src.bookmark;

import com.risingtest.wanted.src.bookmark.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Bookmark> findByRecruitIdAndUserId(long recruitId, long userId);

    List<Bookmark> findAllByUserIdAndStatus(long userId, int status);


    @Query(value = "select b.recruit_id from bookmark b where b.user_id = ?1 and b.status = ?2",nativeQuery = true)
    List<Long> findRecruitIdByUserIdAndStatus(long userIdx, int status);
}
