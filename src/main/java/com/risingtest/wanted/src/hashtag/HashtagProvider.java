package com.risingtest.wanted.src.hashtag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashtagProvider {

    @Autowired
    private HashtagRepository hashtagRepository;

    public List<BasicHashtag> findAll(){
        return hashtagRepository.findAll()
                .stream().map(BasicHashtag::from)
                .collect(Collectors.toList());
    }
}
