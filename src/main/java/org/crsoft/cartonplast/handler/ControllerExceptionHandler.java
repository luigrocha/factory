package org.crsoft.cartonplast.handler;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * @author jyepez on 30/5/2022
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException exception, WebRequest request){
        return new ResponseEntity<>(exception.getMessage(),new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsertException.class)
    public ResponseEntity<?> handleInsertException(InsertException exception, WebRequest request){
        return new ResponseEntity<>(exception.getMessage(),new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UpdateException.class)
    public ResponseEntity<?> handleUpdateException(UpdateException exception, WebRequest request){
        return new ResponseEntity<>(exception.getMessage(),new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
