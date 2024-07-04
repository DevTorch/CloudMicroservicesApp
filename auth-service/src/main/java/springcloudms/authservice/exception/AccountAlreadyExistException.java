package springcloudms.authservice.exception;

public class AccountAlreadyExistException extends RuntimeException{

    public AccountAlreadyExistException(String message) {
        super(message);
    }

    public AccountAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
