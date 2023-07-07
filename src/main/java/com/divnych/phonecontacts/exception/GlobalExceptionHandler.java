package com.divnych.phonecontacts.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DuplicateContactFoundException.class)
    public ResponseEntity<Object> handleDuplicateContactFound(DuplicateContactFoundException ex) {
        String message = ex.getMessage();
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<Object> handleContactNotFound(ContactNotFoundException ex) {
        String message = ex.getMessage();
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(ContactImageNotFoundException.class)
    public ResponseEntity<Object> handleContactImageNotFound(ContactImageNotFoundException ex) {
        String message = ex.getMessage();
        return ResponseEntity.badRequest().body(message);
    }

}