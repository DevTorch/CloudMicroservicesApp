package springcloudms.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CustomerRequestSignUpDTO(
        @NotNull Long accountId,
        @NotBlank String fullName,
        @NotBlank String nickname,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        LocalDateTime persistDateTime
        ) {
}
