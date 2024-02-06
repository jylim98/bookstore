package com.example.book.oauth.common.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class OAuthMember {
    private String profileImageUrl;
    private String nickname;
    private String email;

    @Builder
    public OAuthMember(String profileImageUrl, String nickname, String email) {
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.email = email;
    }
}
