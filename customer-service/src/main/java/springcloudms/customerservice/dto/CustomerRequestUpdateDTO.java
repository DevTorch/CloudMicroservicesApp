package springcloudms.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CustomerRequestUpdateDTO(
        @NotNull Long accountId,
        String fullName,
        String nickname
        ) {
}
