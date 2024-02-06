package com.example.book.oauth.kakao.mapper;

import com.example.book.oauth.common.domain.OAuthMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Test
    void insertTest() {
        OAuthMember oAuthMember = OAuthMember.builder()
                .email("disking12@naver.com")
                .nickname("임종엽")
                .profileImageUrl(null)
                .build();
        int result = memberMapper.insertMember(oAuthMember);
        System.out.println("result = " + result);
    }

    @Test
    void selectTest() {
        OAuthMember oAuthMember = memberMapper.selectMember(1L);
        System.out.println("oAuthMember = " + oAuthMember);
    }

}