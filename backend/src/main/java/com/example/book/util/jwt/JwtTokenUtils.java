package com.example.book.util.jwt;

import com.example.book.common.error.CustomException;
import com.example.book.common.error.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

import static com.example.book.common.error.ErrorCode.*;

@Component
@Log4j2
public class JwtTokenUtils {

    private final Key key;

    public JwtTokenUtils(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * JWT 토큰 생성
     * @param subject - 사용자
     * @param expiredAt - 만료기간
     * @return - JWT 토큰
     */
    public String generate(String subject, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                . compact();
    }

    /**
     * 사용자 정보 추출
     * @param accessToken - 엑세스 토큰
     * @return - 사용자 정보
     */
    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return claims.getSubject();
    }

    /**
     * JWT 토큰 파싱
     * @param accessToken - JWT 토큰
     * @return - 클레임
     */
    public Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    /**
     * JWT 토큰 유효성 검사
     * @param token - JWT 토큰
     * @return - 유효성 검사 결과
     */
    public boolean validateToken(String token) {
        try {
            if (token != null && token.startsWith("Bearer")) {
                token = token.substring(7);
            }
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature", e);
            throw new CustomException(INVALID_SIGNATURE);
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT token", e);
            throw new CustomException(MALFORMED_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
            throw new CustomException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
            throw new CustomException(UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty", e);
            throw new CustomException(INVALID_CLAIMS);
        }
    }
}
