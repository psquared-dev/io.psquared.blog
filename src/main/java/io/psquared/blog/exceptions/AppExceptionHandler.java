package io.psquared.blog.exceptions;

import io.psquared.blog.exceptions.type.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> exceptionMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        System.out.println(exception.getBindingResult().getFieldErrors());
        HashMap<String, String> payload = new HashMap<>();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            payload.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        AppResponse appResponse = new AppResponse("Validation errors occurred", LocalDateTime.now(), true, payload);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(appResponse);
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<Object> handler(NotFound exception, WebRequest request) {

        AppResponse appResponse = new AppResponse(exception.getMessage(), LocalDateTime.now(), true);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(appResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handler(Exception exception, WebRequest request) {

        AppResponse appResponse = new AppResponse(exception.getMessage(), LocalDateTime.now(), true);

        exception.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(appResponse);
    }
}
