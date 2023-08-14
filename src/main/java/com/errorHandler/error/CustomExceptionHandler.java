package com.errorHandler.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
// @ControllerAdvice
public class CustomExceptionHandler {

    // CustomException
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("[handleCustomException] {} : {}",e.getErrorCode().name(), e.getErrorCode().getMessage());
        return ErrorResponse.error(e);
    }

    // Exception
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("[handleException] ", e);
        return ErrorResponse.error(new CustomException(ErrorCode.UnExpectedError));
    }
}
