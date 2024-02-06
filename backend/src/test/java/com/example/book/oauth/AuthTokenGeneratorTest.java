package com.example.book.oauth;

import com.example.book.oauth.common.domain.AuthToken;
import com.example.book.oauth.common.utils.AuthTokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthTokenGeneratorTest {

    @Autowired
    AuthTokenGenerator authTokenGenerator;

    @Test
    void test() {
        AuthToken generate = authTokenGenerator.generate(5L);
        System.out.println("generate = " + generate);

        Long id = authTokenGenerator.extractMemberId("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1IiwiZXhwIjoxNzA3MTg2NTIwfQ.9mRnKBGgsmM6wuTvFpfv3Q3aq8BwgqUrRfFB8x87XcaTNLH672z-pOHbYzzgDDG-3mqGw4LjkU8eG_9mFe9TBg");
        System.out.println("id = " + id);
    }

}