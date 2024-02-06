package com.example.book.oauth.common.controller;

import com.example.book.oauth.common.service.OAuthService;
import com.example.book.oauth.common.domain.OAuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/oauth/")
@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("kakao")
    public String tokenCreate(@RequestParam(name = "code") String code) throws IOException, InterruptedException {
        return oAuthService.createToken(code);
    }

    @PostMapping("kakao/user")
    public OAuthMember userDetails(@RequestParam(name = "authorizationCode") String authorizationCode) throws IOException, InterruptedException {
        return oAuthService.getUserInfo(authorizationCode);
    }
}
