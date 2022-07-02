package com.risingtest.wanted.src.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Bookmark> findByRecruitIdAndUserId(long recruitId, long userId);

    List<Bookmark> findAllByUserId(long userId);
}