package com.example.book.controller;

import com.example.book.common.error.CustomException;
import com.example.book.domain.AuthToken;
import com.example.book.service.oauth.OAuthService;
import com.example.book.util.jwt.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.book.common.error.ErrorCode.INVALID_TOKEN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/")
public class OAuthController {

    private final OAuthService oAuthService;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("kakao")
    public AuthToken kakaoLogin(@RequestParam(name = "code") String code) {
        return oAuthService.loginByKakao(code);
    }

    @PostMapping("naver")
    public AuthToken naverLogin(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        return oAuthService.loginByNaver(code, state);
    }

    @GetMapping("test")
    public String test(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (!jwtTokenUtils.validateToken(token)) {
            throw new CustomException(INVALID_TOKEN);
        }
        return "test";
    }
}
