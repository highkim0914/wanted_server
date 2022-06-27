package com.risingtest.wanted.src.techstack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechstackProvider {

    @Autowired
    private TechstackRepository techstackRepository;

    public List<BasicTechstack> findAll(){
        return techstackRepository.findAll()
                .stream().map(BasicTechstack::from)
                .collect(Collectors.toList());

    }
}
