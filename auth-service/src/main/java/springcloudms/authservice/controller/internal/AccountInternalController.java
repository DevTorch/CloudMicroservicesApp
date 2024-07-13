package springcloudms.authservice.controller.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloudms.authservice.dto.account.response.AccountResponseDTO;
import springcloudms.authservice.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountInternalController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{accountId}/exists")
    public Boolean isAccountExists(@PathVariable("accountId") Long accountId) {
        return accountService.isAccountExists(accountId);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable("accountId") Long accountId) {
        return accountService.findAccountById(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
