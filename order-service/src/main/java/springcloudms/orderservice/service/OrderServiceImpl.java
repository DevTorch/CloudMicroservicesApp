package springcloudms.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.orderservice.client.InventoryFeignClient;
import springcloudms.orderservice.dto.OrderRequestDTO;
import springcloudms.orderservice.dto.ProductRequestDTO;
import springcloudms.orderservice.exception.InventoryExistException;
import springcloudms.orderservice.model.AccountOrder;
import springcloudms.orderservice.model.OrderComplete;
import springcloudms.orderservice.repository.AccOrderRepository;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final AccOrderRepository accOrderRepository;
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
        //TODO: переформулировать Order & Co. Тут, по идее, сеттим OrderComplete в AccauntOrder по accountId
        OrderComplete orderComplete = new OrderComplete();
    }

    @Override
    @Transactional
    public void createAccountOrder(AccountOrder accountOrder) {
        accOrderRepository.save(accountOrder);
    }
}
