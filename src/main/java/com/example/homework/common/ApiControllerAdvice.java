package com.example.homework.common;


import com.example.homework.exception.ErrorCode;
import com.example.homework.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidException(MethodArgumentNotValidException exception) {
        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;

        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(exception, errorCode));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception exception) {
        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;

        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(exception));
    }


    private ErrorResponse makeErrorResponse(Exception exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }

    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(validationErrorList)
                .build();
    }
}
