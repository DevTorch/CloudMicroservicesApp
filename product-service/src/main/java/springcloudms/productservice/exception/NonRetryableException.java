package springcloudms.productservice.exception;

public class NonRetryableException extends RuntimeException{
    public NonRetryableException(String message) {
        super(message);
    }

    public NonRetryableException(Throwable cause) {
        super(cause);
    }

    public NonRetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}
