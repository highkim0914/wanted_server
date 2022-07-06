package com.risingtest.wanted.src.user.model;

import com.risingtest.wanted.src.resume.model.BasicResume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilesOfUser {
    private String jobGroup;
    private List<String> positions;
    private int career;
    private LookingForJobStatus lookingForJob;
    private long profilesResumeId;

    private List<BasicResume> resumes;

    public static ProfilesOfUser from(User user) {
        return ProfilesOfUser.builder()
                .profilesResumeId(user.getProfilesResumeId())
                .jobGroup(user.getJobGroup())
                .positions(Arrays.stream(user.getPositions().split(","))
                        .collect(Collectors.toList()))
                .career(user.getCareer())
                .lookingForJob(user.getLookingForJob())
                .build();
    }
}
