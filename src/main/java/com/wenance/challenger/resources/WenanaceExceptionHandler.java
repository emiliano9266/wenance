package com.wenance.challenger.resources;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class WenanaceExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseError> handleResourceNotFoundException(final ResourceNotFoundException ex, final HttpServletRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<ResponseError>(new ResponseError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseError> handleResourceNotFoundException(final MissingServletRequestParameterException ex, final HttpServletRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<ResponseError>(new ResponseError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
