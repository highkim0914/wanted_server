package com.risingtest.wanted.src.sms;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.utils.JwtService;
import com.risingtest.wanted.utils.ValidationRegex;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    private final JwtService jwtService;

    @Autowired
    public SmsController(SmsService smsService, JwtService jwtService) {
        this.smsService = smsService;
        this.jwtService = jwtService;
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
    public BaseResponse<PostSmsAuthenticationRes> authenticateCode(@RequestBody PostSmsAuthenticationReq smsAuthenticationDto){
        if(!ValidationRegex.isRegexPhoneNumber(smsAuthenticationDto.getPhoneNumber())){
            return new BaseResponse<>(BaseResponseStatus.SMS_INVALID_PHONE_NUMBER);
        }
        String phoneNumber = smsAuthenticationDto.getPhoneNumber().replace("-","");
        String code = smsAuthenticationDto.getCode();
        if(smsService.isValidCode(phoneNumber, code)) {
            String jwt = jwtService.createJwtUsingPhoneNumber(phoneNumber);
            return new BaseResponse<>(new PostSmsAuthenticationRes(jwt));
        }
        else
            return new BaseResponse<>(BaseResponseStatus.SMS_WRONG_CODE);

    }
}
