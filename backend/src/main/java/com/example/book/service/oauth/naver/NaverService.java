package com.example.book.service.oauth.naver;

import com.example.book.common.error.CustomException;
import com.example.book.domain.OAuthMember;
import com.example.book.service.oauth.OAuthProvider;
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

import static com.example.book.common.error.ErrorCode.INVALID_AUTH_CODE;
import static com.example.book.common.error.ErrorCode.UNKNOWN_ERROR;

@Service
@RequiredArgsConstructor
@Log4j2
public class NaverService {

    private static final String GRANT_TYPE = "authorization_code";

    private final RestTemplate restTemplate;

    @Value("${oauth.naver.client_secret}")
    private String CLIENT_SECRET;

    @Value("${oauth.naver.client_id}")
    private String CLIENT_ID;

    @Value("${oauth.naver.token_url}")
    private String TOKEN_URL;

    @Value("${oauth.naver.api_url}")
    private String API_URL;

    public String getAccessToken(String code, String state) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("code", code);
        params.add("state", state);

        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> accessTokenResponse = restTemplate.exchange(
                TOKEN_URL,
                HttpMethod.POST,
                naverTokenRequest,
                String.class
        );

        JSONObject jsonObject = new JSONObject(accessTokenResponse.getBody());

        if (!jsonObject.has("access_token")) {
            throw new CustomException(INVALID_AUTH_CODE);
        }
        return jsonObject.getString("access_token");

    }

    public OAuthMember getUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> userInfoResponse = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                naverTokenRequest,
                String.class
        );
        System.out.println("userInfoResponse = " + userInfoResponse);

        JSONObject jsonObject = new JSONObject(userInfoResponse.getBody());
        System.out.println("jsonObject = " + jsonObject);

        JSONObject naver_account = jsonObject.getJSONObject("response");
        return OAuthMember.builder()
                .email(null)
                .nickname(naver_account.getString("nickname"))
                .profileImageUrl(naver_account.getString("profile_image"))
                .oAuthProvider(OAuthProvider.NAVER)
                .build();

    }
}
