package com.risingtest.wanted.src.resume.model;

import com.risingtest.wanted.src.award.BasicAward;
import com.risingtest.wanted.src.career.BasicCareer;
import com.risingtest.wanted.src.education.BasicEducation;
import com.risingtest.wanted.src.jobapplication.model.BasicJobApplication;
import com.risingtest.wanted.src.language.BasicLanguageSkill;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResumeDto {
    private long id;

    private String title;

    private String name;

    private String email;

    private String phoneNumber;

    private String introduction;

    private String externalLink;

    private List<BasicCareer> careers;

    private List<BasicAward> awards;

    private List<BasicEducation> educations;

    private List<BasicLanguageSkill> languageSkills;

    private List<String> skills;

    private List<BasicJobApplication> jobApplications;

    public static ResumeDto from(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .title(resume.getTitle())
                .name(resume.getName())
                .email(resume.getEmail())
                .phoneNumber(resume.getPhoneNumber())
                .introduction(resume.getIntroduction())
                .externalLink(resume.getExternalLink())
                .skills(Arrays.asList(resume.getSkills().split(",")))
                .careers(resume.getCareers()
                        .stream()
                        .map(BasicCareer::from)
                        .collect(Collectors.toList()))
                .awards(resume.getAwards()
                        .stream()
                        .map(BasicAward::from)
                        .collect(Collectors.toList()))
                .educations(resume.getEducations()
                        .stream()
                        .map(BasicEducation::from)
                        .collect(Collectors.toList()))
                .languageSkills(resume.getLanguageSkills()
                        .stream()
                        .map(BasicLanguageSkill::from)
                        .collect(Collectors.toList()))
                .jobApplications(resume.getJobApplications()
                        .stream()
                        .map(BasicJobApplication::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
