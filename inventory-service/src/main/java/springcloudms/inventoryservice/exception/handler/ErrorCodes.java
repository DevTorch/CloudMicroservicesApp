package springcloudms.inventoryservice.exception.handler;

public enum ErrorCodes {
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    INVALID_INPUT(400, "Invalid input"),
    NOT_FOUND(404, "Not found");

    private final int code;
    private final String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorCodes getErrorCode() {
        return this.message == null ? null : this;
    }
}
