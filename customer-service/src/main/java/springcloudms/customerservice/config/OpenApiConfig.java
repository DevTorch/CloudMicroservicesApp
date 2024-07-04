package springcloudms.customerservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customerServiceAPI() {

        return new OpenAPI()
                .info(new Info().title("Customer Service API").version("v0.0.1")
                        .description("REST API for Order Service")
                        .contact(new Contact().url("https://github.com/DevTorch/"))
                        .license(new License().name("Open Source Code")
                                .url("https://github.com/DevTorch/")))
                .externalDocs(new ExternalDocumentation()
                        .description("Demo OpenDOCs description")
                        .url("https://github.com/DevTorch/"));
    }
}
