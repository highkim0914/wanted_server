package com.risingtest.wanted.src.sms;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.sms.model.PostSmsAuthenticationReq;
import com.risingtest.wanted.src.sms.model.PostSmsAuthenticationRes;
import com.risingtest.wanted.src.sms.model.PostSmsReq;
import com.risingtest.wanted.utils.JwtService;
import com.risingtest.wanted.utils.ValidationRegex;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    @Autowired
    public SmsController(SmsService smsService, JwtService jwtService) {
        this.smsService = smsService;
    }
    @PostMapping
    public BaseResponse<String> createAuthenticationCode(@RequestBody PostSmsReq postSmsReq){
        if(!ValidationRegex.isRegexPhoneNumber(postSmsReq.getPhoneNumber())){
            return new BaseResponse<>(BaseResponseStatus.SMS_INVALID_PHONE_NUMBER);
        }
        try {
            String phoneNumber = postSmsReq.getPhoneNumber().replace("-","");
            SingleMessageSentResponse singleMessageSentResponse = smsService.sendOne(phoneNumber);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/authentication")
    public BaseResponse<PostSmsAuthenticationRes> authenticateCode(@RequestBody PostSmsAuthenticationReq postSmsAuthenticationReq){
        if(!ValidationRegex.isRegexPhoneNumber(postSmsAuthenticationReq.getPhoneNumber())){
            return new BaseResponse<>(BaseResponseStatus.SMS_INVALID_PHONE_NUMBER);
        }
        try {
            PostSmsAuthenticationRes postSmsAuthenticationRes = smsService.authenticate(postSmsAuthenticationReq);
            return new BaseResponse<>(postSmsAuthenticationRes);
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }
}
