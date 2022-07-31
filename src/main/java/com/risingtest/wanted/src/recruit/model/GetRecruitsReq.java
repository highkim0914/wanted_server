package com.risingtest.wanted.src.recruit.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetRecruitsReq {
    private String jobGroup;
    private List<Integer> years;
    private List<String> positions;
    private List<String> locations;
    private List<Long> hashtags;
    private List<Long> techstacks;
    private int size;
    private int page;
    private String sortString;

    GetRecruitsReq(){
        jobGroup = "";
        positions = new ArrayList<>();
        locations = new ArrayList<>();
        hashtags = new ArrayList<>();
        techstacks = new ArrayList<>();
        years = new ArrayList<>();
        years.add(0);
        size = 20;
        page = 0;
        sortString = "responseRate,desc";
    }
}
