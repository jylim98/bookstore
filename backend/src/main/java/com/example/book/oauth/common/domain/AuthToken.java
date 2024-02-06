package com.example.book.oauth.common.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class AuthToken {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    @Builder
    public AuthToken(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
        this.expiresIn = expiresIn;
    }
}
