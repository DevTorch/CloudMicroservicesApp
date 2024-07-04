package springcloudms.authservice.dto.customer;

import jakarta.validation.constraints.NotNull;

public record CustomerUpdateRequestDTO(
        @NotNull Long accountId,
        String fullName,
        String nickname
        ) {
}
