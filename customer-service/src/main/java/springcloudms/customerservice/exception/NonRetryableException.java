package springcloudms.customerservice.exception;

public class NonRetryableException extends RuntimeException {
    public NonRetryableException() {
    }

    public NonRetryableException(String message) {
        super(message);
    }

    public NonRetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}
