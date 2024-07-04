package springcloudms.authservice.exception;

public class CustomerServiceResponseException extends RuntimeException {
    public CustomerServiceResponseException(String reason) {
        super(reason);
    }

    public CustomerServiceResponseException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
