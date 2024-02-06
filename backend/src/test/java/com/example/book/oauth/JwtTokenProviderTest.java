package com.example.book.oauth;

import com.example.book.oauth.common.utils.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void test() {
        Date date = new Date();
        String jwt = jwtTokenProvider.generate("TEST", date);
        String subject = jwtTokenProvider.extractSubject(jwt);
        System.out.println("subject = " + subject);
    }
}