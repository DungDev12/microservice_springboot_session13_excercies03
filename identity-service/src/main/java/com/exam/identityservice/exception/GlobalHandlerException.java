package com.exam.identityservice.exception;

import com.exam.identityservice.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                               HttpServletRequest request){
        List<Map<String,String>> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String > error = new HashMap<>();
                    error.put("field", fieldError.getField());
                    error.put("message",fieldError.getDefaultMessage());
                    return error;
                }).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .error(errors)
                        .path(request.getRequestURI())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build()
        );
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e,HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .error(e.getMessage())
                        .path(request.getRequestURI())
                        .status(HttpStatus.NOT_FOUND.value())
                        .build()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(AccessDeniedException e,HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse.builder()
                        .error(e.getMessage())
                        .path(request.getRequestURI())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build()
        );
    }
}
