package springcloudms.customerservice.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springcloudms.customerservice.dto.AuthenticationResponseDTO;
import springcloudms.customerservice.exception.AuthenticationServiceException;

@FeignClient(name = "auth-service", path = "/api/internal/account",
        fallbackFactory = AuthenticationServiceFeignClient.AuthFeignClientFallback.class)
public interface AuthenticationServiceFeignClient {

    @GetMapping("/{accountId}")
    ResponseEntity<AuthenticationResponseDTO> getAccountById(@PathVariable Long accountId);

    @GetMapping("/{accountId}/exists")
    Boolean isAccountExists(@PathVariable Long accountId);

    @Component
    class AuthFeignClientFallback  implements FallbackFactory<FallbackWithFactory> {
        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory(cause.getMessage());
        }
    }

    record FallbackWithFactory(String reason) implements AuthenticationServiceFeignClient {

        @Override
        public ResponseEntity<AuthenticationResponseDTO> getAccountById(Long accountId) {
            String reason = "Account with id %s not found".formatted(accountId);
            throw  new AuthenticationServiceException(reason);
        }

        @Override
        public Boolean isAccountExists(Long accountId) {
            String reason = "Account with id %s not exist".formatted(accountId);
            throw  new AuthenticationServiceException(reason);
        }
    }
}
