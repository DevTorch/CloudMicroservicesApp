package springcloudms.authservice.exception;

public class FeignRequestException extends Exception{

    public FeignRequestException(String message) {
        super(message);
    }

    public FeignRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
