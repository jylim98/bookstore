package com.example.book.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OAuthUser {
    private String profileImageUrl;
    private String nickname;
    private String email;
}
