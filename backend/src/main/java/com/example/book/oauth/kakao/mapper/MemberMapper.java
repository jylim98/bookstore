package com.example.book.oauth.kakao.mapper;

import com.example.book.oauth.common.domain.OAuthMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    OAuthMember selectMember(Long id);
    int insertMember(OAuthMember oAuthMember);
}
