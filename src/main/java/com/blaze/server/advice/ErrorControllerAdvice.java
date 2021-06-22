package com.blaze.server.advice;

import com.mongodb.MongoTimeoutException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorControllerAdvice {
    
    @ExceptionHandler(MongoTimeoutException.class)
    public ResponseEntity<String> handleMongoTimeout(MongoTimeoutException mongoTimeoutException) {
        return new ResponseEntity<String>("The connection to the DataBase is down.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleMongoTimeout(HttpMessageNotReadableException httpMessageNotReadableException) {
        return new ResponseEntity<String>("Request data is not correct. ", HttpStatus.BAD_REQUEST);
    }

}
