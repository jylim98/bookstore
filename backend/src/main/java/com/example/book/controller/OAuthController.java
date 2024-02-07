package com.example.book.controller;

import com.example.book.domain.AuthToken;
import com.example.book.service.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/")
public class OAuthController {

    private final OAuthService oAuthService;
    @PostMapping("kakao")
    public AuthToken kakaoLogin(@RequestParam(name = "code") String code) {
        return oAuthService.loginByKakao(code);
    }

    @PostMapping("naver")
    public AuthToken naverLogin(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        return oAuthService.loginByNaver(code, state);
    }
}
