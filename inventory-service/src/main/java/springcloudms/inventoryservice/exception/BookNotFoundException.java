package springcloudms.inventoryservice.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbnNo) {
        super(String.format("Book with ISBN %s not found", isbnNo));
    }
}
