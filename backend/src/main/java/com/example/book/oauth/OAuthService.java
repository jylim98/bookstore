package com.example.book.oauth;

import java.io.IOException;

public interface OAuthService {

    /**
     * 인증 코드를 통해 엑세스 토큰을 발급
     * @param code - 인증 코드
     * @return - 엑세스 토큰
     */
    String createToken(String code) throws IOException, InterruptedException;

    /**
     * 엑세스 토큰을 통해 사용자 정보 조회
     * @param accessToken - 엑세스 토큰
     * @return - 사용자 정보
     */
    OAuthUser getUserInfo(String accessToken) throws IOException, InterruptedException;
}
