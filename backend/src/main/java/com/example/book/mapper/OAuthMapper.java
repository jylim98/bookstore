package com.example.book.mapper;

import com.example.book.domain.OAuthMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OAuthMapper {

    OAuthMember findByEmail(String email);

    int insertMember(OAuthMember oAuthMember);

}
