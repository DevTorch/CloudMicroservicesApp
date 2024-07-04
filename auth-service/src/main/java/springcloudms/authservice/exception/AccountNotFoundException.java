package springcloudms.authservice.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Exception e) {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }}
