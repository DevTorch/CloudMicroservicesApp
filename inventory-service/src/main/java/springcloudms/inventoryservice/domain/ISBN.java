package springcloudms.inventoryservice.domain;

import org.apache.commons.validator.routines.ISBNValidator;

import java.io.Serializable;

public record ISBN(String value) implements Serializable {
    private static final ISBNValidator ISBN_VALIDATOR = new ISBNValidator();

    public ISBN {
        if (!ISBN_VALIDATOR.isValid(value)) {
            throw new IllegalArgumentException("Invalid ISBN" + value);
        }
    }
}
