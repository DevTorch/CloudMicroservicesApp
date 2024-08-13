package springcloudms.inventoryservice.domain;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.UUID;

public record ProductId(UUID id) implements Serializable {

    public ProductId {
        Assert.notNull(id, "id must not be null");
    }

    public ProductId() {
        this(UUID.randomUUID());
    }
}
