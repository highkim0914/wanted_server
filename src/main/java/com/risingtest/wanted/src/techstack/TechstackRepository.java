package com.risingtest.wanted.src.techstack;

import com.risingtest.wanted.src.techstack.model.Techstack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechstackRepository extends JpaRepository<Techstack,Long> {
}
