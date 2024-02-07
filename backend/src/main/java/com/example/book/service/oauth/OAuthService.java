package com.example.book.service.oauth;

import com.example.book.domain.AuthToken;
import com.example.book.domain.OAuthMember;
import com.example.book.mapper.OAuthMapper;
import com.example.book.service.oauth.naver.NaverService;
import com.example.book.service.oauth.kakao.KakaoService;
import com.example.book.util.jwt.AuthTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OAuthService {

    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final AuthTokenUtils authTokenUtils;
    private final OAuthMapper oAuthMapper;

    public AuthToken loginByKakao(String code) {
        String accessToken = kakaoService.getAccessToken(code);
        OAuthMember oAuthMember = kakaoService.getUserInfo(accessToken);

        Long memberId = findOrCreateMember(oAuthMember);
        log.info("사용자 식별 값 결과 : {}", memberId);

        return authTokenUtils.generate(memberId);
    }

    public AuthToken loginByNaver(String code, String state) {
        String accessToken = naverService.getAccessToken(code, state);
        OAuthMember oAuthMember = naverService.getUserInfo(accessToken);

        Long memberId = findOrCreateMember(oAuthMember);

        log.info("사용자 식별 값 결과 : {}", memberId);

        return authTokenUtils.generate(memberId);
    }

    public Long findOrCreateMember(OAuthMember oAuthMember) {
        OAuthMember findMember = oAuthMapper.findByEmail(oAuthMember.getEmail());
        log.info("데이터베이스 사용자 조회 결과 : {}", findMember);

        //처음 로그인 한 회원이면 데이터베이스에 사용자 저장
        if (findMember == null) {
            log.info("************* 첫 로그인 데이터베이스 사용자 정보 저장 *************");
            oAuthMapper.insertMember(oAuthMember);
            return oAuthMember.getId();
        } else {
            return findMember.getId();
        }
    }
}
