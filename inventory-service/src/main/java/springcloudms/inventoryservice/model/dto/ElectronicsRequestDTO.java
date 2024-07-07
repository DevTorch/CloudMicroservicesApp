package springcloudms.inventoryservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link springcloudms.inventoryservice.model.ElectronicsEntity}
 */
@Builder
public record ElectronicsRequestDTO(@NotNull String articleNo,
                                    String title,
                                    Integer quantity) implements Serializable {
}