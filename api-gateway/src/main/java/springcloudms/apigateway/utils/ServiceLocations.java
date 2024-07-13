package springcloudms.apigateway.utils;

import lombok.Getter;

@Getter
public enum ServiceLocations {

    AUTH_SERVICE("lb://auth-service"),
    PRODUCT_SERVICE("lb://product-service"),
    ORDER_SERVICE("lb://order-service"),
    INVENTORY_SERVICE("lb://inventory-service"),
    CUSTOMER_SERVICE("lb://customer-service"),
    API_GATEWAY("lb://api-gateway");

    private final String uri;

    ServiceLocations(String uri) {
        this.uri = uri;
    }
}
