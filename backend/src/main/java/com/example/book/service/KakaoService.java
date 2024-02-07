package com.example.book.service;

import com.example.book.common.error.CustomException;
import com.example.book.domain.OAuthMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.example.book.common.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class KakaoService {

    private final RestTemplate restTemplate;

    @Value("${oauth.kakao.client_id}")
    private String CLIENT_ID;

    @Value("${oauth.kakao.token_url}")
    private String TOKEN_URL;

    @Value("${oauth.kakao.api_url}")
    private String API_URL;

    private static final String GRANT_TYPE = "authorization_code";

    /**
     * 카카오 엑세스 토큰 발급
     * @param code - 인가 코드
     * @return - 엑세스 토큰
     */
    public String getAccessToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", CLIENT_ID);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, httpHeaders);

        try {
            ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                    TOKEN_URL,
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );

            JSONObject jsonObject = new JSONObject(accessTokenResponse.getBody());
            return jsonObject.getString("access_token");

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().toString().equals("400 BAD_REQUEST")) {
                throw new CustomException(INVALID_AUTH_CODE);
            }
            throw new CustomException(UNKNOWN_ERROR);
        }
    }

    /**
     * 카카오 사용자 정보 조회
     * @param accessToken - 액세스 토큰
     * @return - 사용자 정보
     */
    public OAuthMember getUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> kakaoUserRequest = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> userInfoResponse = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                kakaoUserRequest,
                String.class
        );

        JSONObject jsonObject = new JSONObject(userInfoResponse.getBody());

        JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");
        JSONObject profile = kakao_account.getJSONObject("profile");

        return OAuthMember.builder()
                .email(kakao_account.getString("email"))
                .nickname(profile.getString("nickname"))
                .profileImageUrl(profile.getString("profile_image_url"))
                .build();
    }
}
