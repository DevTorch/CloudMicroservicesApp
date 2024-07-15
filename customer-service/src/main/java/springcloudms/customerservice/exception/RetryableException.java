package springcloudms.customerservice.exception;

public class RetryableException extends RuntimeException {
    public RetryableException() {
    }

    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}
