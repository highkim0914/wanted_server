package com.risingtest.wanted.src.career.model;

import com.risingtest.wanted.src.result.model.BasicResult;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicCareer {
    private long id;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private TenureType tenure;

    private Boolean isInService;

    private String companyName;

    private String departmentPosition;

    private List<BasicResult> results;

    public static BasicCareer from(Career career){
        return BasicCareer.builder()
                .id(career.getId())
                .startDate(career.getStartDate())
                .endDate(career.getEndDate())
                .tenure(career.getTenure())
                .isInService(career.getIsInService())
                .companyName(career.getCompanyName())
                .departmentPosition(career.getDepartmentPosition())
                .results(career.getResults().stream()
                        .map(BasicResult::from)
                        .collect(Collectors.toList()))
                .build();
    }

    public Career toEntity() {
        Career.CareerBuilder careerBuilder =  Career.builder();
        if(id!=0){
            careerBuilder.id(id);
        }
        careerBuilder.startDate(startDate);
        careerBuilder.endDate(endDate);
        careerBuilder.tenure(tenure);
        careerBuilder.isInService(isInService);
        careerBuilder.companyName(companyName);
        careerBuilder.departmentPosition(departmentPosition);
        careerBuilder.results(results.stream()
                .map(BasicResult::toEntity)
                .collect(Collectors.toList()));
        return careerBuilder.build();
    }
}
