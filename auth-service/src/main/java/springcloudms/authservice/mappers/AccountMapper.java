package springcloudms.authservice.mappers;

import org.springframework.stereotype.Component;
import springcloudms.authservice.dto.account.response.AccountResponseDTO;
import springcloudms.authservice.model.Account;
import springcloudms.authservice.model.Role;

import java.util.stream.Collectors;

@Component
public class AccountMapper {

    public AccountResponseDTO toAccountResponseDTO(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .roles(account.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .isActive(account.getActive())
                .build();
    }
}
