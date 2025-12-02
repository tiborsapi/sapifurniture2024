package ro.sapientia.furniture.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Standard error response model.
 */
@Setter
@Getter
public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
