package com.risingtest.wanted.src.language;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LanguageCertificateService {
    @Autowired
    LanguageCertificateRepository languageCertificateRepository;

    @Transactional
    public void updateLanguageCertificateByBasicLanguageCertificate(List<BasicLanguageCertificate> languageCertificates, LanguageSkill languageSkill) throws BaseException{
        List<LanguageCertificate> languageCertificateList = languageSkill.getLanguageCertificates();
        for(LanguageCertificate languageCertificate : languageCertificateList){
            languageCertificate.setStatus(1);
        }
        for(BasicLanguageCertificate basicLanguageCertificate : languageCertificates){
            LanguageCertificate languageCertificate;
            if(basicLanguageCertificate.getId()!=0){
                languageCertificate = languageCertificateRepository.findById(basicLanguageCertificate.getId())
                        .orElseThrow(()-> new BaseException(BaseResponseStatus.NO_LANGUAGE_CERTIFICATE));
                languageCertificate.setTitle(basicLanguageCertificate.getTitle());
                languageCertificate.setScore(basicLanguageCertificate.getScore());
                languageCertificate.setDate(basicLanguageCertificate.getDate());
                languageCertificate.setStatus(0);
                languageCertificateRepository.save(languageCertificate);
            }
            else{
                LanguageCertificate languageCertificate1 = basicLanguageCertificate.toEntity();
                languageCertificate1.setLanguageSkill(languageSkill);
                languageCertificate = languageCertificateRepository.save(languageCertificate1);
            }
        }
    }
}
