package springcloudms.orderservice.exception;

public class InventoryExistException extends RuntimeException {
    public InventoryExistException(String message) {
        super(message);
    }

    public InventoryExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
