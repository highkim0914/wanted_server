package com.risingtest.wanted.src.result.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicResult {
    private long id;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String detail;

    public static BasicResult from(Result result){
        return BasicResult.builder()
                .id(result.getId())
                .startDate(result.getStartDate())
                .endDate(result.getEndDate())
                .detail(result.getDetail())
                .title(result.getTitle())
                .build();
    }

    public Result toEntity() {
        Result.ResultBuilder resultBuilder =  Result.builder();
        if(id!=0){
            resultBuilder.id(id);
        }
        resultBuilder.startDate(startDate);
        resultBuilder.endDate(endDate);
        resultBuilder.detail(detail);
        resultBuilder.title(title);
        return resultBuilder.build();
    }
}
