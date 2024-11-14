package io.github.lsmcodes.inventorymanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerErrorException;

import com.fasterxml.jackson.core.JsonParseException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getLocalizedMessage());
    }

    @ExceptionHandler(value = CodeAlreadyExistsException.class)
    public ResponseEntity<String> handleCodeAlreadyExistsException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getLocalizedMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getAllErrors().getLast().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class, JsonParseException.class })
    public ResponseEntity<String> handleHttpMessageNotReadableException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getLocalizedMessage());
    }

    @ExceptionHandler(value = ServerErrorException.class)
    public ResponseEntity<String> handleServerErrorException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getLocalizedMessage());
    }

}
