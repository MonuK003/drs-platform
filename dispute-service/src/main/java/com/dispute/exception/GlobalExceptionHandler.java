package com.dispute.exception;

import com.dispute.dto.ApiErrroResponse.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)

    public ResponseEntity<Object>
    handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        Map<String, Object> response =
                new HashMap<>();

        response.put("timestamp",
                LocalDateTime.now());

        response.put("status",
                HttpStatus.NOT_FOUND.value());

        response.put("message",
                ex.getMessage());

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<Object>
    handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()));

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object>
    handleGenericException(
            Exception ex) {

        Map<String, Object> response =
                new HashMap<>();

        response.put("timestamp",
                LocalDateTime.now());

        response.put("status",
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        response.put("message",
                ex.getMessage());

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

   //Handled the user not found

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFound(UserNotFoundException ex){

            ApiErrorResponse response=new ApiErrorResponse(404, ex.getMessage());

             return ResponseEntity
                     .status(HttpStatus.NOT_FOUND)
                     .body(response);

    }

    @ExceptionHandler(UserServiceUnavailableException.class)
    public ResponseEntity<ApiErrorResponse> handleUserServiceUnavailable(
            UserServiceUnavailableException ex) {

        ApiErrorResponse response = new ApiErrorResponse(
                500,
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }


}