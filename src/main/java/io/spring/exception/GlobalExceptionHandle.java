package io.spring.exception;

import io.spring.exception.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        //Get all errors
        Map<String, List<String>> errors = new HashMap();
        for (FieldError s : ex.getBindingResult().getFieldErrors()) {
            String field = s.getField();
            List<String> errorsField = errors.getOrDefault(field, new ArrayList<>());
            errorsField.add(s.getDefaultMessage());
            errors.put(s.getField(), errorsField);
        }
        body.put("errors", errors);
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler()
    protected ResponseEntity<Object> handleMethodArgumentNotValid(ResourceNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("message", ex.getMessage());
        body.put("time_stamp", Instant.now());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}

