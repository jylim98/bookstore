package com.example.book.domain;

import com.example.book.service.oauth.OAuthProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class OAuthMember {

    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private OAuthProvider oAuthProvider;

    @Builder
    public OAuthMember(String email, String nickname, String profileImageUrl, OAuthProvider oAuthProvider) {
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
