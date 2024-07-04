package springcloudms.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springcloudms.apigateway.utils.ServiceLocations;

@Configuration
public class RoutesConfiguration {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service-router", r -> r.path("/api/auth/**")
                        .uri(ServiceLocations.AUTH_SERVICE.getUri()))
                .route("product-service-router", r -> r.path("/api/products/**")
                        .uri(ServiceLocations.PRODUCT_SERVICE.getUri()))
                .route("order-service-router", r -> r.path("/api/orders/**")
                        .uri(ServiceLocations.ORDER_SERVICE.getUri()))
                .route("inventory-service-router", r -> r.path("/api/inventory/**")
                        .uri(ServiceLocations.INVENTORY_SERVICE.getUri()))
                .route("inventory-service-router", r -> r.path("/api/customers/**")
                        .uri(ServiceLocations.CUSTOMER_SERVICE.getUri()))
                .route("product_service_swagger", r -> r.path("/v3/api-docs")
                        .uri(ServiceLocations.PRODUCT_SERVICE.getUri()))
                .route("inventory_service_swagger", r -> r.path("/v3/api-docs")
                        .uri(ServiceLocations.INVENTORY_SERVICE.getUri()))
                .route("customer_service_swagger", r -> r.path("/v3/api-docs")
                        .uri(ServiceLocations.CUSTOMER_SERVICE.getUri()))
                .build();
    }
}
