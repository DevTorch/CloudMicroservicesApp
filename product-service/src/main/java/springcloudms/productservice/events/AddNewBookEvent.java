package springcloudms.productservice.events;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AddNewBookEvent(
        @NotNull String articleNo,
        @NotNull String title,
        @NotNull String author,
        @NotNull String publisher,
        @NotNull String isbnNo
) {
}
