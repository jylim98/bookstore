package com.example.book.oauth.kakao;

import com.example.book.oauth.common.service.OAuthService;
import com.example.book.oauth.common.domain.OAuthMember;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class KakaoService implements OAuthService {

    private final HttpClient httpClient;

    @Value("${oauth.kakao.client_id}")
    private String CLIENT_ID;

    @Value("${oauth.kakao.token_url}")
    private String TOKEN_URL;

    @Value("${oauth.kakao.api_url}")
    private String API_URL;

    @Override
    public String createToken(String code) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=" + "authorization_code" +
                                "&client_id=" + CLIENT_ID +
                                "&code=" + code
                ))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());

        return jsonObject.getString("access_token");
    }

    @Override
    public OAuthMember getUserInfo(String accessToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());

        JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");
        JSONObject profile = kakao_account.getJSONObject("profile");

        return OAuthMember.builder()
                .email(kakao_account.getString("email"))
                .nickname(profile.getString("nickname"))
                .profileImageUrl(profile.getString("profile_image_url"))
                .build();
    }
}
