package com.risingtest.wanted.src.language;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.language.model.BasicLanguageSkill;
import com.risingtest.wanted.src.language.model.LanguageSkill;
import com.risingtest.wanted.src.resume.model.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LanguageSkillService {
    @Autowired
    LanguageSkillRepository languageSkillRepository;

    @Autowired
    LanguageCertificateService languageCertificateService;

    @Transactional
    public void updateLanguageSkillByBasicLanguageSkill(List<BasicLanguageSkill> languageSkills, Resume resume) throws BaseException{
        List<LanguageSkill> languageSkills1 = resume.getLanguageSkills();
        if(!languageSkills1.isEmpty()) {
            for (LanguageSkill languageSkill : languageSkills1) {
                languageSkill.setStatus(1);
            }
        }
        for(BasicLanguageSkill basicLanguageSkill : languageSkills){
            LanguageSkill languageSkill;
            if(basicLanguageSkill.getId()!=0){
                languageSkill = languageSkillRepository.findById(basicLanguageSkill.getId())
                        .orElseThrow(()-> new BaseException(BaseResponseStatus.NO_LANGUAGE_SKILL));
                languageSkill.setTitle(basicLanguageSkill.getTitle());
                languageSkill.setLevel(basicLanguageSkill.getLevel());
                languageCertificateService.updateLanguageCertificateByBasicLanguageCertificate(basicLanguageSkill.getLanguageCertificates(), languageSkill);
                languageSkill.setStatus(0);
                languageSkillRepository.save(languageSkill);
            }
            else{
                LanguageSkill languageSkill1 = basicLanguageSkill.toEntity();
                languageSkill1.setResume(resume);
                languageSkill = languageSkillRepository.save(languageSkill1);
                languageCertificateService.updateLanguageCertificateByBasicLanguageCertificate(basicLanguageSkill.getLanguageCertificates(), languageSkill);
            }
            languageSkills1.add(languageSkill);
        }
//        return languageSkills1;
    }
}
