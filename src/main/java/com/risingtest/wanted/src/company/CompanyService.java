package com.risingtest.wanted.src.company;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.company.model.PostCompanyReq;
import com.risingtest.wanted.src.follow.model.BasicFollow;
import com.risingtest.wanted.src.follow.model.Follow;
import com.risingtest.wanted.src.follow.FollowService;
import com.risingtest.wanted.src.recruit.model.PostRecruitReq;
import com.risingtest.wanted.src.recruit.RecruitRepository;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.user.UserProvider;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private FollowService followService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.seperator}")
    private String seperator;

    private final String COMPANY_IMAGE_PREFIX = "company_image_";
    private final String COMPANY_IMAGE_FOLDER = "companies";

    public Company createCompany(PostCompanyReq postCompanyReq) throws BaseException {
        try {
            Company company = companyRepository.save(postCompanyReq.toEntity());
            return company;
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public Recruit createRecruit(PostRecruitReq postRecruitReq, Company company) {
        try {
            Recruit recruit = postRecruitReq.toEntity(company);
            recruitRepository.save(recruit);
            return recruit;
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public String uploadCompanyImagesAndSetPhotoUrl(long id, List<MultipartFile> images) throws BaseException{
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_COMPANY));

        StringJoiner resultJoiner = new StringJoiner(",");
        int count = 1;
        try {
            for(MultipartFile file : images){
                //logger.info("uploaded image original file name : "+ originalFileName);
                resultJoiner.add(uploadFile(file,id,count));
                count++;
            }
        }
        catch (BaseException e){
            throw e;
        }
        String photoUrl = resultJoiner.toString();
        company.setPhotoUrl(photoUrl);
        return photoUrl;
    }

    public String uploadCompanyProfileImageAndProfilePhotoUrl(long id, MultipartFile file) throws BaseException{
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_COMPANY));
        String profilePhotoUrl = uploadFile(file,id,0);
        company.setProfilePhotoUrl(profilePhotoUrl);
        return profilePhotoUrl;
    }

    public String uploadFile(MultipartFile file, long id, long suffixNumber) throws BaseException{
        String originalFileName = file.getOriginalFilename();
        String[] split = originalFileName.split("\\.");
        if(split.length!=2) {
            throw new BaseException(BaseResponseStatus.UPLOAD_IMAGE_INVALID_FILENAME);
        }
        try {
            String type = split[1];

            String saveFileImage = COMPANY_IMAGE_PREFIX + id + "-" + suffixNumber +"." + type;

            String[] pathString = new String[]{uploadDir, COMPANY_IMAGE_FOLDER, saveFileImage};

            Path path = Paths.get(Arrays.stream(pathString)
                    .map(String::valueOf)
                    .collect(Collectors.joining(seperator, "", "")));

            //logger.info(path.toString());

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            StringJoiner joiner = new StringJoiner(seperator, seperator, "");
            joiner.add("resources");
            joiner.add("images");
            joiner.add(COMPANY_IMAGE_FOLDER);
            joiner.add(saveFileImage);
            return joiner.toString();
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.UPLOAD_IMAGE_FAIL);
        }
    }

    @Transactional
    public BasicFollow toggleFollow(long companyId) throws BaseException{
        Company company = checkCompanyId(companyId);
        User user = userProvider.findUserWithUserJwtToken();

        Follow follow = followService.toggleFollow(company,user);
        return BasicFollow.from(follow);
    }

    private Company checkCompanyId(long companyId) throws BaseException{
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_COMPANY));
        return company;
    }
}
