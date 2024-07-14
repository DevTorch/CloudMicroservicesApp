package springcloudms.apigateway.utils;

import lombok.Getter;


public enum ServiceLocations {

    API_GATEWAY("lb://api-gateway"),
    AUTH_SERVICE("lb://auth-service"),
    CUSTOMER_SERVICE("lb://customer-service"),
    INVENTORY_SERVICE("lb://inventory-service"),
    ORDER_SERVICE("lb://order-service"),
    PRODUCT_SERVICE("lb://product-service");

    private final String uri;

    ServiceLocations(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}

