package springcloudms.inventoryservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import springcloudms.inventoryservice.client.InventoryRestClient;

//@Configuration
public class RestClientConfiguration {

//    @Value("${eureka.instance.hostname}:${server.port}")
//    private String baseUrl;
////    @Bean
//    public InventoryRestClient inventoryRestClient() {
//        RestClient restClient = RestClient.builder()
//                .baseUrl(baseUrl)
//                .build();
//
//        var restClientAdapter = RestClientAdapter.create(restClient);
//        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
//
//        return httpServiceProxyFactory.createClient(InventoryRestClient.class);
//    }
}
