package com.risingtest.wanted.src.resume;

import com.risingtest.wanted.src.resume.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findAllByUserId(Long userId);

    long countByUserId(long userId);
}
