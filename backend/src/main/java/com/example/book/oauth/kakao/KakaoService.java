package com.example.book.oauth.kakao;

import com.example.book.oauth.OAuthService;
import com.example.book.oauth.OAuthUser;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class KakaoService implements OAuthService {

    private static final String CLIENT_ID = "3c74a5584f931b80b80464d2cd6c09bd";

    @Override
    public String createToken(String code) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://kauth.kakao.com/oauth/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=" + "authorization_code" +
                                "&client_id=" + CLIENT_ID +
//                                "&redirect_uri=" + kakaoRedirectUri +
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
                .uri(URI.create("https://kapi.kakao.com/v2/user/me"))
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
