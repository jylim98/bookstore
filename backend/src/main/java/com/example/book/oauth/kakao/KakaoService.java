package com.example.book.oauth.kakao;

import com.example.book.oauth.OAuthService;
import com.example.book.oauth.OAuthUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class KakaoService implements OAuthService {


    @Value("${oauth.kakao.client_id}")
    private String CLIENT_ID;

    @Value("${oauth.kakao.token_url}")
    private String TOKEN_URL;

    @Value("${oauth.kakao.api_url}")
    private String API_URL;

    @Override
    public String createToken(String code) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
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
    public OAuthUser getUserInfo(String accessToken) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());

        JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");
        JSONObject profile = kakao_account.getJSONObject("profile");

        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setEmail(kakao_account.getString("email"));
        oAuthUser.setNickname(profile.getString("nickname"));
        oAuthUser.setProfileImageUrl(profile.getString("profile_image_url"));

        return oAuthUser;
    }
}
