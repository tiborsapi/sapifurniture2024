package ro.sapientia.furniture.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(
            MethodArgumentNotValidException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error",
                ex.getBindingResult().getFieldError().getDefaultMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleServiceUnavailable(
            ServiceUnavailableException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", "Service temporarily unavailable.");

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(
        ResourceNotFoundException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", "Resource not found.");

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(error);
    }

}
