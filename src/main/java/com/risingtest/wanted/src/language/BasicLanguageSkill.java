package com.risingtest.wanted.src.language;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.src.resume.model.Resume;
import com.risingtest.wanted.src.resume.model.ResumeDto;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicLanguageSkill {
    private long id;

    private String title;

    private LanguageLevel level;

    private List<BasicLanguageCertificate> languageCertificates;

    public static BasicLanguageSkill from(LanguageSkill languageSkill) {
        return BasicLanguageSkill.builder()
                .id(languageSkill.getId())
                .title(languageSkill.getTitle())
                .level(languageSkill.getLevel())
                .languageCertificates(languageSkill.getLanguageCertificates()
                        .stream()
                        .map(BasicLanguageCertificate::from)
                        .collect(Collectors.toList()))
                .build();
    }

    public LanguageSkill toEntity() {
        LanguageSkill.LanguageSkillBuilder builder = LanguageSkill.builder();
        if(id!=0){
            builder.id(id);
        }
        builder.title(title);
        builder.level(level);
        builder.languageCertificates(languageCertificates.stream()
                .map(BasicLanguageCertificate::toEntity)
                .collect(Collectors.toList()));
        return builder.build();
    }
}
