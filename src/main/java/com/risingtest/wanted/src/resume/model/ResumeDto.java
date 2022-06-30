package com.risingtest.wanted.src.resume.model;

import com.risingtest.wanted.src.award.Award;
import com.risingtest.wanted.src.career.Career;
import com.risingtest.wanted.src.education.Education;
import com.risingtest.wanted.src.jobapplication.JobApplication;
import com.risingtest.wanted.src.language.LanguageSkill;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    private List<Career> careers;

    private List<Award> awards;

    private List<Education> educations;

    private List<LanguageSkill> languageSkills;

    private List<JobApplication> jobApplications;

    public static ResumeDto from(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .title(resume.getTitle())
                .name(resume.getName())
                .email(resume.getEmail())
                .phoneNumber(resume.getPhoneNumber())
                .introduction(resume.getIntroduction())
                .externalLink(resume.getExternalLink())
                .careers(resume.getCareers())
                .awards(resume.getAwards())
                .educations(resume.getEducations())
                .languageSkills(resume.getLanguageSkills())
                .jobApplications(resume.getJobApplications())
                .build();
    }
}
