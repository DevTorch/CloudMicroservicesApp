package springcloudms.authservice.dto.account.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AccountSignUpDTO(
//        @NotNull Long accountId,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String fullName,
        @NotBlank String nickname,
        @NotBlank String about,
        @NotBlank String city,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        LocalDateTime persistDateTime
        ) {
}
