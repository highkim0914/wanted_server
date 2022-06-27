package com.risingtest.wanted.src.recruit.dto;

import com.risingtest.wanted.src.hashtag.BasicHashtag;
import com.risingtest.wanted.src.hashtag.Hashtag;
import com.risingtest.wanted.src.techstack.BasicTechstack;
import com.risingtest.wanted.src.techstack.Techstack;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitRes {
    private List<BasicRecruitRes> recruits;

    private List<BasicHashtag> hashtags;

    private List<BasicTechstack> techstacks;

}
