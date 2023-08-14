package com.errorHandler;

import com.errorHandler.error.CustomException;
import com.errorHandler.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
public class TestController {

    // Exception 테스트
    @PostMapping("/test1")
    public Map<String, Object> test1() throws Exception {
        throw new Exception();
    }

    // CustomExeption 테스트
    @PostMapping("/test2")
    public Map<String, Object> test2() {
        throw new CustomException(ErrorCode.ForbiddenException);
    }

    // HttpStatus를 OK가 아닌 값으로 지정한 CustomError
    @PostMapping("/test3")
    public Map<String, Object> test3() {
        throw new CustomException(ErrorCode.BadRequestError);
    }

    // 코드에서 Exception이 발생할 경우
    @PostMapping("/test4")
    public Map<String, Object> test4() {
        Map<String, Object> outMap = new HashMap<>();
        log.error(outMap.get("error").toString()); // 에러 발생 지점(NullPointerException) -> Exception Handler로 넘어감

        throw new CustomException(ErrorCode.ForbiddenException);
    }

    // 생성된 클래스에서 Exception이 발생될 경우
    @PostMapping("/test5")
    public Map<String, Object> test5() {
        TestService testService = new TestService();
        testService.serviceError(); // 생성된 클래스에서 에러 발생(NullPointerException) -> Exception Handler로 넘어감
        throw new CustomException(ErrorCode.ForbiddenException);
    }

    // 생성된 클래스에서 CustomException이 발생될 경우
    @PostMapping("/test6")
    public Map<String, Object> test6() {
        TestService testService = new TestService();
        testService.serviceError2(); // 생성된 클래스에서 에러 발생(NullPointerException) -> CustomException Handler로 넘어감

        Map<String, Object> outMap = new HashMap<>();
        outMap.put("data", "hello");
        return outMap;
    }

    // 생성된 클래스에서 NullPointerException, Exception을 구분해서 처리
    // Exception 종류를 나눠 세밀한 처리를 하는것이 에러를 추적하기에 효과적입니다.
    // 주로 이 방법을 사용할 것을 추천
    @PostMapping("/test7")
    public Map<String, Object> test7() {
        TestService testService = new TestService();
        testService.serviceError3(); // 생성된 클래스에서 에러 발생(NullPointerException) -> CustomException Handler로 넘어감

        Map<String, Object> outMap = new HashMap<>();
        outMap.put("data", "hello");
        return outMap;
    }

    public class TestService {
        public void serviceError() {
            Map<String, Object> outMap = new HashMap<>();
            log.error(outMap.get("error").toString());
        }

        public void serviceError2() {
            Map<String, Object> outMap = new HashMap<>();
            try {
                log.error(outMap.get("error").toString());
            } catch (Exception e) {
                throw new CustomException(ErrorCode.ParameterError);
            }
        }

        public void serviceError3() {
            Map<String, Object> outMap = new HashMap<>();
            try {
                log.error(outMap.get("error").toString());
            } catch (NullPointerException ne) {
                log.error("[NullPointerException] ", ne);
                throw new CustomException(ErrorCode.ParameterError);
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
