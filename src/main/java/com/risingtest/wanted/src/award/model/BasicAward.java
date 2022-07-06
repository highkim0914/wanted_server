package com.risingtest.wanted.src.award.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicAward {
    private long id;

    private LocalDate date;

    private String title;

    private String detail;

    public static BasicAward from(Award award){
        return BasicAward.builder()
                .id(award.getId())
                .date(award.getDate())
                .title(award.getTitle())
                .detail(award.getDetail())
                .build();
    }

    public Award toEntity() {
        Award.AwardBuilder awardBuilder =  Award.builder();
        if(id!=0){
            awardBuilder.id(id);
        }
        awardBuilder.date(date);
        awardBuilder.title(title);
        awardBuilder.detail(detail);
        return awardBuilder.build();
    }
}
