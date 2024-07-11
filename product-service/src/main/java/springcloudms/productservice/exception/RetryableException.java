package springcloudms.productservice.exception;

public class RetryableException extends RuntimeException {
    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(Throwable cause) {
        super(cause);
    }

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}
