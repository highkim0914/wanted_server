package com.risingtest.wanted.src.follow;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.src.follow.model.BasicFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/follows")
public class FollowController {

    @Autowired
    FollowProvider followProvider;

    @GetMapping
    public BaseResponse<List<BasicFollow>> getFollows(){
        try {
            List<BasicFollow> basicFollows = followProvider.findAllByUserToken()
                    .stream()
                    .map(BasicFollow::from)
                    .filter(basicFollow -> basicFollow.getStatus()==0)
                    .collect(Collectors.toList());
            return new BaseResponse<>(basicFollows);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
