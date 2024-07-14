package springcloudms.orderservice.service;

import springcloudms.orderservice.dto.OrderRequestDTO;

public interface OrderService {
    void placeOrder(OrderRequestDTO orderRequestDTO);
}
