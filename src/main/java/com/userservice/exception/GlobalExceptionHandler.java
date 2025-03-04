package com.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex){

        List<ErrorResponse.FieldError> errors=ex.getBindingResult()
                                                .getFieldErrors()
                                                 .stream()
                                                 .map(fieldError -> new ErrorResponse.FieldError(
                                                         fieldError.getField(),
                                                         fieldError.getDefaultMessage()
                                                 )).collect(Collectors.toList());

        ErrorResponse errorResponse= new ErrorResponse("Failure Becuse:",errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

  }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); // 404
    }

}
