package springcloudms.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springcloudms.orderservice.dto.OrderRequestDTO;
import springcloudms.orderservice.service.OrderService;
import springcloudms.orderservice.service.OrderServiceImpl;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        orderService.placeOrder(orderRequestDTO);
        return ("Order placed successfully");
    }

}
