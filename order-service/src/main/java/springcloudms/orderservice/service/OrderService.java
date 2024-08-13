package springcloudms.orderservice.service;

import springcloudms.orderservice.dto.OrderRequestDTO;
import springcloudms.orderservice.model.AccountOrder;

public interface OrderService {
    void placeOrder(OrderRequestDTO orderRequestDTO);

    void createAccountOrder(AccountOrder accountOrder);
}
