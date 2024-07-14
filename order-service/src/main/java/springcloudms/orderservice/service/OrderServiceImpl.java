package springcloudms.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springcloudms.orderservice.client.InventoryFeignClient;
import springcloudms.orderservice.dto.OrderRequestDTO;
import springcloudms.orderservice.dto.ProductRequestDTO;
import springcloudms.orderservice.exception.InventoryExistException;
import springcloudms.orderservice.model.OrderComplete;
import springcloudms.orderservice.repository.OrderRepository;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryFeignClient inventoryFeignClient;

    public void placeOrder(OrderRequestDTO orderRequestDTO) {

        Boolean isInStock = inventoryFeignClient.isInStock(ProductRequestDTO.builder()
                .articleNo(orderRequestDTO.articleNo())
                .productType(orderRequestDTO.productType())
                .quantity(orderRequestDTO.quantity()).build());

        if (Boolean.FALSE.equals(isInStock)) {
            throw new InventoryExistException(
                    String.format("Product with skuCode: %s is not in stock in quantity: %s",
                            orderRequestDTO.articleNo(), orderRequestDTO.quantity())
            );
        }
        //TODO: переформулировать Order & Co.
        OrderComplete orderComplete = new OrderComplete();

//        orderRepository.save(order);
    }
}
