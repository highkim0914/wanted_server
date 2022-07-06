package com.risingtest.wanted.src.recruit.model;

import com.risingtest.wanted.src.bookmark.model.BasicBookmark;
import com.risingtest.wanted.src.recruit.model.BasicRecruitRes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitsAndBookmarksRes {
    List<BasicRecruitRes> recruits;
    List<BasicBookmark> bookmarks;
}
