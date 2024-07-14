package springcloudms.authservice.exception;

public class KafkaSenderException extends RuntimeException {
    public KafkaSenderException(String message) {
        super(message);
    }

    public KafkaSenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public KafkaSenderException(Throwable cause) {
        super(cause);
    }
}
