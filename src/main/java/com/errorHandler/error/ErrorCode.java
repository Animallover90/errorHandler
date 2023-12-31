package com.errorHandler.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SUCCESS("200", "OK", HttpStatus.OK),

    // HttpStatus를 정상값으로 하고 받는 쪽에서 처리하도록 code 값을 별도로 전달
    ForbiddenException("403", "해당 요청에 대한 권한이 없습니다.", HttpStatus.OK),
    UnExpectedError("500", "예상치 못한 에러가 발생하였습니다.", HttpStatus.OK),
    ParameterError("453", "파라미터 값에 문제가 있습니다.", HttpStatus.OK), // HttpStatus enum값에 없는 값으로 커스텀 지정

    // HttpStatus를 지정
    BadRequestError ("400", "정상적인 요청이 아닙니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;

}
