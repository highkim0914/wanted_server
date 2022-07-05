package com.risingtest.wanted.src.user;



import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.user.model.PostUserReq;
import com.risingtest.wanted.src.user.model.PostUserRes;
import com.risingtest.wanted.src.user.model.User;
import com.risingtest.wanted.utils.JwtService;
import com.risingtest.wanted.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.risingtest.wanted.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserProvider userProvider;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.separator}")
    private String separator;

    private final String USER_IMAGE_PREFIX = "user_image_";
    private final String USER_IMAGE_FOLDER = "users";

    @Autowired
    public UserService(UserProvider userProvider, JwtService jwtService, UserRepository userRepository) {
        this.userProvider = userProvider;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Transactional
    public String saveUserImageAndPatchUserImageUrl(MultipartFile file) throws BaseException {
        User user = userProvider.findUserWithUserJwtToken();
        String photoUrl = saveUserImage(user,file);
        patchUserImageUrl(user,photoUrl);
        return photoUrl;
    }

    public String saveUserImage(User user, MultipartFile file) throws BaseException {

        long userId = user.getId();
        String originalFileName = file.getOriginalFilename();
        logger.info("uploaded image original file name : "+ originalFileName);

        String[] split = originalFileName.split("\\.");
        if(split.length!=2) {
            throw new BaseException(BaseResponseStatus.UPLOAD_IMAGE_INVALID_FILENAME);
        }
        String type = split[1];

        String saveFileImage = USER_IMAGE_PREFIX + userId + "." + type;

        String[] pathString = new String[]{uploadDir, USER_IMAGE_FOLDER, saveFileImage};

        Path path = Paths.get(Arrays.stream(pathString)
                .map(String::valueOf)
                .collect(Collectors.joining(separator,"","")));

        logger.info("파일 경로 : " + path.toString());
        try {
            byte[] bytes = file.getBytes();
            Files.write(path,bytes);

            //file.transferTo(path);

            //InputStream in = file.getInputStream();
            //Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }


        StringJoiner joiner = new StringJoiner(separator, separator,"");
        //joiner.add("resources");
        joiner.add("images");
        joiner.add(USER_IMAGE_FOLDER);
        joiner.add(saveFileImage);
        return joiner.toString();
    }

    public void patchUserImageUrl(User user, String photoUrl) throws BaseException{
        try {
            user.setPhotoUrl(photoUrl);
            userRepository.save(user);
        }
        catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteUserImageAndPatchUserImageUrl(long userId) throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        deleteUserImage(user);
        patchUserImageUrl(user,"");
    }

    public void deleteUserImage(User user) throws BaseException{
        try {
            String photoUrl = user.getPhotoUrl();
            String fileName = getImageFileNameFromPhotoUrl(photoUrl);
            File file = new File(uploadDir + File.separator + "users" + File.separator + fileName);
            if(!file.delete()){
                throw new BaseException(BaseResponseStatus.DELETE_IMAGE_FAIL);
            }
        }
        catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public String getImageFileNameFromPhotoUrl(String photoUrl){
        String[] arr = photoUrl.split(File.separator);
        return arr[arr.length-1];
    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.isAlreadyRegisteredEmail(postUserReq.getEmail())){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        if(!jwtService.getPhoneNumber().equals(postUserReq.getPhoneNumber())){
            throw new BaseException(INVALID_JWT);
        }

        String pwd;
        try{
            //암호화
            pwd = SHA256.encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            User user = userRepository.save(postUserReq.toEntity());
            long userIdx = user.getId();
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void setCompany(Company company) throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        user.setCompany(company);
        user = userRepository.save(user);
    }


    public void saveUser(User user) {
        userRepository.save(user);
    }
}
