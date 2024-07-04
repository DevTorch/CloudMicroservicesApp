package springcloudms.customerservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationResponseDTO(
        @Schema(description = "Account Id на сервере аутентификации")
        @NotBlank Long accountId,

        @Schema(description = "E-mail пользователя")
        @Email @NotBlank String email
) {
}
