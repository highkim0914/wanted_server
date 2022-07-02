package com.risingtest.wanted.src.language;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.resume.model.Resume;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Where(clause = "status = 0")
public class LanguageSkill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private LanguageLevel level;

    @ManyToOne
    @JoinColumn(name = "resume_id",nullable = false,updatable = false)
    @JsonBackReference
    private Resume resume;

    @OneToMany(mappedBy = "languageSkill")
    @JsonManagedReference
    @ToString.Exclude
    @Builder.Default
    private List<LanguageCertificate> languageCertificates = new ArrayList<>();
}
