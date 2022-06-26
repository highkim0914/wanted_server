package com.risingtest.wanted.src.sms;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.secret.Secret;
import com.risingtest.wanted.utils.JwtService;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.risingtest.wanted.config.BaseResponseStatus.SMS_ERROR;

@Service
public class SmsService {

    final DefaultMessageService messageService;

    private Map<String, String> phoneNumberCodeMap = new HashMap<>();

    public SmsService() {
        this.messageService = NurigoApp.INSTANCE.initialize(Secret.SMS_API_KEY, Secret.SMS_API_SECRET_KEY, "https://api.coolsms.co.kr");
    }

    public SingleMessageSentResponse sendOne(String phoneNumber) throws BaseException {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(Secret.SMS_API_CALLER);
        message.setTo(phoneNumber);
        String randomString = String.valueOf(generateAuthNo1());
        message.setText("인증번호 ["+ randomString + "] 를 입력해주세요");
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        if(response.getStatusCode().equals("2000")){
            phoneNumberCodeMap.put(phoneNumber,randomString);
        }
        else {
            throw new BaseException(SMS_ERROR);
        }
        System.out.println(response);

        return response;
    }

    public boolean isValidCode(String phoneNumber, String code) {
        if(phoneNumberCodeMap.get(phoneNumber).equals(code)){
            phoneNumberCodeMap.remove(phoneNumber);
            return true;
        }
        else
            return false;
    }

    public int generateAuthNo1() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }

}