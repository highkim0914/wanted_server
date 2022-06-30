package com.risingtest.wanted.src.hashtag;

import com.risingtest.wanted.src.hashtag.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {
}
