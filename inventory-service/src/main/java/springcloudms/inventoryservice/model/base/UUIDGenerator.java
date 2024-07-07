package springcloudms.inventoryservice.model.base;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator {
    @Bean
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
