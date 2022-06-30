package com.risingtest.wanted.src.techstack.model;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicTechstack {
    private long id;

    private String name;

    public static BasicTechstack from(Techstack techstack){
        return new BasicTechstack(techstack.getId(), techstack.getName());
    }
}
