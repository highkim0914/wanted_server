package com.risingtest.wanted.src.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    Optional<Bookmark> findByRecruitIdAndUserId(long recruitId, long userId);

    List<Bookmark> findAllByUserId(long userId);
}
