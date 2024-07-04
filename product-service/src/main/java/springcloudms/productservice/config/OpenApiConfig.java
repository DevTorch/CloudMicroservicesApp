package springcloudms.productservice.config;

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
    public OpenAPI productServiceAPI() {

        return new OpenAPI()
                .info(new Info().title("Product Service API").version("v0.0.1")
                        .description("REST API for Product Service")
                        .contact( new Contact().url("https://github.com/DevTorch/") )
                        .license(new License().name("Open Source Code")))
                .externalDocs(new ExternalDocumentation()
                        .description("Demo OpenDOCs description")
                        .url("https://github.com/DevTorch/"));
    }
}
