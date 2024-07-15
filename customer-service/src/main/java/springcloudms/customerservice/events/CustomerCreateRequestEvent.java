package springcloudms.customerservice.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CustomerCreateRequestEvent(
        @NotNull Long accountId,
        @NotBlank String fullName,
        @NotBlank String nickname,
        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDateTime persistDateTime
) {
}
