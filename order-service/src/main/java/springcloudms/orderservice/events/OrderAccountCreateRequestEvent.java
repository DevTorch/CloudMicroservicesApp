package springcloudms.orderservice.events;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderAccountCreateRequestEvent(
        @NotNull Long accountId,
        LocalDateTime createdTimeStamp
) {
}
