package com.example.book.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_AUTH_CODE(BAD_REQUEST, "인가 코드가 유효하지 않습니다."),
    UNKNOWN_ERROR(INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생하였습니다."),
    INVALID_TOKEN(BAD_REQUEST, "토큰 정보가 유효하지 않습니다."),
    INVALID_SIGNATURE(BAD_REQUEST, "서명 정보가 유효하지 않습니다."),
    MALFORMED_TOKEN(BAD_REQUEST, "토큰 형식이 올바르지 않습니다."),
    EXPIRED_TOKEN(BAD_REQUEST, "토큰이 유효기간이 만료되었습니다."),
    UNSUPPORTED_TOKEN(BAD_REQUEST, "지원하지 않는 토큰입니다."),
    INVALID_CLAIMS(BAD_REQUEST, "유효하지 않은 클레임입니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
