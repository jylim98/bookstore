package com.example.book.util.jwt;

import com.example.book.domain.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokenUtils {

    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    private final JwtTokenUtils jwtTokenUtils;

    /**
     * 서비스 토큰 생성
     * @param memberId - 사용자 식별값
     * @return - 서비스 토큰
     */
    public AuthToken generate(Long memberId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = memberId.toString();

        String accessToken = jwtTokenUtils.generate(subject, accessTokenExpiredAt);
        String refrestToken = jwtTokenUtils.generate(subject, refreshTokenExpiredAt);

        return AuthToken.builder()
                .accessToken(accessToken)
                .refreshToken(refrestToken)
                .grantType(BEARER_TYPE)
                .expiresIn(ACCESS_TOKEN_EXPIRE_TIME / 1000L)
                .build();
    }

    /**
     * 사용자 식별값 추출
     * @param accessToken - 서비스 토큰
     * @return - 사용자 식별값
     */
    public Long extractSubject(String accessToken) {
        return Long.valueOf(jwtTokenUtils.extractSubject(accessToken));
    }
}
