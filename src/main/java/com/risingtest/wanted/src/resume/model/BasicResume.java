package com.risingtest.wanted.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BasicResume {

    private long id;

    private String title;

    private LocalDateTime updatedAt;

    private Boolean isFinished;


    public static BasicResume from(Resume resume) {
        return BasicResume.builder()
                .id(resume.getId())
                .title(resume.getTitle())
                .updatedAt(resume.getUpdatedAt())
                .isFinished(resume.getIsFinished())
                .build();
    }
}
