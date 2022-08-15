package com.blog.itstory.global.error;

import com.blog.itstory.global.error.exception.BusinessException;
import com.blog.itstory.global.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // @RequestParam 바인딩 오류
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e){
        //  커스텀 예외가 아니므로, ErrorCode 를 통한 예외 생성 과정을 거치지 않았음
        List<String> messages = new ArrayList<>();
        e.getConstraintViolations().stream()
                .forEach(exception -> messages.add(exception.getMessage()));

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, messages);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    //  @ResponseBody 바인딩 오류
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindingException(BindException e){

        //  커스텀 예외가 아니므로, ErrorCode 를 통한 예외 생성 과정을 거치지 않았음
        List<String> messages = new ArrayList<>();

        //  e.getBindingResult().getFieldErrors() 도 가능함
        //  if(bindingResult.hasErrors()) 가 필요할까? 일단은 필요 없어보임
        e.getFieldErrors().stream()
                .forEach(error -> messages.add(error.getDefaultMessage()));
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, messages);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    //  Method not Allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e){
        //  405 에러 반환할 것. 리스트 생성
        List<String> messages = List.of(ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED, messages);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    /**
     *  이 앱에서는, ENUM타입의 category에 잘못된 값을 바인딩 시도했을 때 나온다.
     *  예시) ?category=database
     *  현재 카테고리값이 들어오면 toUpperCase()해서 ENUM에 바인딩시키기 때문에, 잘못된 값 입력 시 해당 예외 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        //  ErrorResponse 에 추가할 리스트 생성
        List<String> messages = List.of(ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, messages);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e){
        //  ErrorResponse 에 추가할 에러메시지 리스트 생성
        List<String> message = List.of(e.getMessage());

        //  해당 Custom Exception 에 있는 status 를 HttpStatus 로 바꿈.
        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatus());

        //  Response 생성 후 반환
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, message);

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        //  ErrorResponse 에 추가할 리스트 생성. stackTrace 는 클라이언트에게 보여주지 않는 게 좋을 듯.
        List<String> messages = List.of(e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, messages);
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}





















