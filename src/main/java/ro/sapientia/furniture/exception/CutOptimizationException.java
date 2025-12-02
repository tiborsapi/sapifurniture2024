package ro.sapientia.furniture.exception;

public class CutOptimizationException extends RuntimeException{

    public CutOptimizationException(String message) {
        super(message);
    }

    public CutOptimizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
