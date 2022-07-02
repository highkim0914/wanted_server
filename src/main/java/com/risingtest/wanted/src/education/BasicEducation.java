package com.risingtest.wanted.src.education;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.risingtest.wanted.src.resume.model.ResumeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicEducation {
    private long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isInService;

    private String schoolName;

    private String majorDegree;

    private String detail;

    public static BasicEducation from(Education education){
        return BasicEducation.builder()
                .id(education.getId())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .isInService(education.getIsInService())
                .schoolName(education.getSchoolName())
                .majorDegree(education.getMajorDegree())
                .detail(education.getDetail())
                .build();
    }

    public Education toEntity() {
        Education.EducationBuilder educationBuilder =  Education.builder();
        if(id!=0){
            educationBuilder.id(id);
        }
        educationBuilder.startDate(startDate);
        educationBuilder.endDate(endDate);
        educationBuilder.isInService(isInService);
        educationBuilder.schoolName(schoolName);
        educationBuilder.majorDegree(majorDegree);
        educationBuilder.detail(detail);
        return educationBuilder.build();
    }
}
