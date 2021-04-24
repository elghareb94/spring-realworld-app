package io.spring.exception;

import io.spring.exception.exceptions.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;
import java.time.Instant;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
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

    //    @ExceptionHandler({ResourceNotFoundException.class})
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(ResourceNotFoundException ex) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("exception", "ResourceNotFoundException");
//        body.put("status", HttpStatus.NOT_FOUND.value());
//        body.put("message", ex.getMessage());
//        body.put("time_stamp", Instant.now());
//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    }
    @ExceptionHandler({NoResultException.class})
    protected ResponseEntity<Object> handleNoResultException(NoResultException ex) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("exception", "NoResultException");
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("message", ex.getMessage());
        body.put("time_stamp", Instant.now());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("exception", "AuthenticationException");
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("time_stamp", Instant.now());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("exception", "IllegalStateException");
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("time_stamp", Instant.now());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("exception", "Exception");
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.EXPECTATION_FAILED.value());
        body.put("time_stamp", Instant.now());

        return new ResponseEntity<>(body, HttpStatus.EXPECTATION_FAILED);
    }


}

