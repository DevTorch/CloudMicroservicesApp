package springcloudms.authservice.dto.account.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountLoginRequestDTO(
        @NotNull
        @NotBlank
        @Email String email,
        @NotNull @NotBlank String password
) {
}
