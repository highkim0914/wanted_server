package com.risingtest.wanted.src.language.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicLanguageCertificate {
    private long id;

    private String title;

    private String score;

    private LocalDate date;

    public static BasicLanguageCertificate from(LanguageCertificate languageCertificate) {
        return BasicLanguageCertificate.builder()
                .id(languageCertificate.getId())
                .title(languageCertificate.getTitle())
                .score(languageCertificate.getScore())
                .date(languageCertificate.getDate())
                .build();
    }

    public LanguageCertificate toEntity() {
        LanguageCertificate.LanguageCertificateBuilder languageCertificateBuilder =  LanguageCertificate.builder();
        if(id!=0){
            languageCertificateBuilder.id(id);
        }
        languageCertificateBuilder.title(title);
        languageCertificateBuilder.score(score);
        languageCertificateBuilder.date(date);
        return languageCertificateBuilder.build();
    }
}
