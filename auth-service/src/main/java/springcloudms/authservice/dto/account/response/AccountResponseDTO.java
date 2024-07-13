package springcloudms.authservice.dto.account.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import springcloudms.authservice.model.Role;
import springcloudms.authservice.model.RoleNameEnum;

import java.util.Set;

@Builder
public record AccountResponseDTO(
        Long id,
        @NotEmpty @Email String email,
        @NotEmpty Set<Role> roles,
        Boolean isActive
) {
}
