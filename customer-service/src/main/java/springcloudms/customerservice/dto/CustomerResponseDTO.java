package springcloudms.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Schema(description = "Customer  DTO Model")
public record CustomerResponseDTO(
        @Schema(description = "Customer Id")
        @NotNull Long id,
        @Schema(description = "Customer's full name")
        @NotNull String fullName,
        @Schema(description = "Customer's nickname")
        String nickname,
        @Schema(description = "Customer's email")
        @Email @Nullable String email,
        @Schema(description = "Customer's creation date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        LocalDateTime registrationDate) implements Serializable {

        public CustomerResponseDTO(Long id,
                                   String fullName,
                                   String nickname,
                                   LocalDateTime registrationDate) {
                this(id, fullName, nickname, null, registrationDate);
        }
}
