package com.example.book.controller;

import com.example.book.domain.AuthToken;
import com.example.book.domain.OAuthMember;
import com.example.book.service.KakaoService;
import com.example.book.service.OAuthService;
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
    public AuthToken login(@RequestParam(name = "code") String code) {
        return oAuthService.login(code);
    }
}
