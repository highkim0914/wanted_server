package com.risingtest.wanted.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    REQUEST_METHOD_ERROR(false,2004,"요청 메소드가 잘못되었습니다."),
    SMS_INVALID_PHONE_NUMBER(false, 2005, "전화번호 형식을 확인해주세요."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_INVALID_PHONE_NUMBER(false,2018,"전화번호 형식을 확인해주세요."),
    POST_USERS_EMPTY_TOKEN(false,2019,"전화번호 인증을 진행해주세요."),
    POST_USERS_INVALID_JWT(false, 2020, "유효하지 않은 전화번호 토큰입니다."),
    INVALID_EMPLOYEE_NUMBER(false, 2021, "적절한 직원수 입력이 아닙니다. (10인 이상 필수)"),
    INVALID_ESTABLISHMENT_YEAR(false,2022,"적절한 설립년도가 아닙니다."),
    INVALID_REGISTRATION_NUMBER(false,2023,"적절한 사업자번호가 아닙니다."),

    LOGIN_USER_NO_EMAIL(false,2030,"존재하지 않는 이메일입니다."),

    GET_RECRUIT_NO_RECRUIT(false, 2060, "존재하지 않는 채용 공고입니다."),
    GET_RECRUIT_TOO_MANY_YEARS(false, 2061, "years는 최대 2개까지만 사용 가능합니다."),

    POST_BOOKMARK_EXISTS(false,2070,"이미 북마크되어 있는 채용공고입니다."),
    NO_BOOKMARK(false,2071,"북마크되지 않은 채용공고입니다."),
    NO_COMPANY(false,2080,"존재하지 않는 회사입니다."),
    NO_LIKE_MARK(false,2090,"존재하지 않는 좋아요입니다."),
    NO_RESUME(false,2100,"존재하지 않는 이력서입니다."),
    RESUME_NOT_OWNED_BY_USER(false,2101, "현재 유저의 이력서가 아닙니다."),
    NO_FOLLOW(false,2110,"존재하지 않는 팔로잉입니다."),

    UPLOAD_IMAGE_FAIL(false,2500,"이미지 업로드 오류입니다."),
    UPLOAD_IMAGE_INVALID_FILENAME(false,2501,"이미지 파일 이름을 확인해주세요."),
    DELETE_IMAGE_FAIL(false,2502,"이미지 삭제에 실패했습니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    SMS_ERROR(false,4005,"SMS 인증 서비스 오류입니다."),
    SMS_WRONG_CODE(false,4006,"유효하지 않은 인증 코드입니다."),


    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");



    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
