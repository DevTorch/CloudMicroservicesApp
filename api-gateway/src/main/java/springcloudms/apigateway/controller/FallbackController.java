package springcloudms.apigateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/customersFallback")
    public Mono<String> customerServiceFallback() {
        return Mono.just("Customer service too long to respond or is down. Try again later.");
    }

    @RequestMapping("/orderFallback")
    public Mono<String> orderServiceFallback() {
        return Mono.just("Order service too long to respond or is down. Try again later.");
    }

    @RequestMapping("/authFallback")
    public Mono<String> authServiceFallback() {
        return Mono.just("Authentication service too long to respond or is down. Try again later.");
    }
}
