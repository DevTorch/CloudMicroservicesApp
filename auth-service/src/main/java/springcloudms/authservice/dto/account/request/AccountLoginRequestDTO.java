package springcloudms.authservice.dto.account.request;

import jakarta.validation.constraints.NotBlank;

public record AccountLoginRequestDTO(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
