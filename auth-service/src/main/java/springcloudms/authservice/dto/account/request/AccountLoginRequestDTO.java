package springcloudms.authservice.dto.account.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountLoginRequestDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}
