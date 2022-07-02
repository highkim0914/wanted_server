package com.risingtest.wanted.src.likemark;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.src.likemark.model.BasicLikemark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/likemarks")
public class LikemarkController {
    @Autowired
    LikemarkProvider likemarkProvider;

    @GetMapping
    public BaseResponse<List<BasicLikemark>> getLikemarks(){
        try {
            List<BasicLikemark> basicLikemarks = likemarkProvider.findAllByUserToken()
                    .stream()
                    .map(BasicLikemark::from)
                    .filter(basicLikemark -> basicLikemark.getStatus()==0)
                    .collect(Collectors.toList());
            return new BaseResponse<>(basicLikemarks);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
