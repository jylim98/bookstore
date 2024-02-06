package com.example.book.controller;

import com.example.book.domain.AuthToken;
import com.example.book.domain.OAuthMember;
import com.example.book.service.KakaoService;
import com.example.book.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

//    @PostMapping("/oauth/test")
//    public String test(@RequestParam(name = "code") String code) {
//        return kakaoService.getAccessToken(code);
//    }
//
//    @PostMapping("/oauth/test/user")
//    public OAuthMember test2(@RequestParam(name = "token") String token) {
//        return kakaoService.getUserInfo(token);
//    }

    @PostMapping("/oauth/login")
    public AuthToken login(@RequestParam(name = "code") String code) {
        return oAuthService.login(code);
    }
}
