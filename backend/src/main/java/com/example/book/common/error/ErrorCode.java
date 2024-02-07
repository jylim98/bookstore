package com.example.book.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_AUTH_CODE(BAD_REQUEST, "인가 코드가 유효하지 않습니다."),
    UNKNOWN_ERROR(INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생하였습니다.");


    private final HttpStatus httpStatus;
    private final String message;

}
