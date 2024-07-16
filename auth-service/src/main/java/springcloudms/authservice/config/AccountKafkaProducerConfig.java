package springcloudms.authservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import springcloudms.authservice.dto.customer.CustomerCreateRequestEvent;
import springcloudms.authservice.dto.order.OrderAccountCreateRequestEvent;
import springcloudms.core.constants.CoreConstants;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AccountKafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.acks}")
    private String acknowledgement;

    @Value("${spring.kafka.producer.properties.linger.ms}")
    private String linger;

    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
    private String deliveryTimeout;

    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
    private String requestTimeout;

    @Value("${spring.kafka.producer.properties.max.in.flight.requests.per.connection}")
    private String maxInFlightRequestsPerConnection;

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS, CustomerCreateRequestEvent.class.getCanonicalName() + ":springcloudms.authservice.dto.customer.CustomerCreateRequestEvent, "
        + OrderAccountCreateRequestEvent.class.getCanonicalName() + ":springcloudms.authservice.dto.order.OrderAccountCreateRequestEvent");
        props.put(ProducerConfig.ACKS_CONFIG, acknowledgement);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeout);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequestsPerConnection);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); //Default

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    NewTopic createSignUpTopic() {
//        String signUpTopic = "customer-signup-events-topic";
        return TopicBuilder.name(CoreConstants.CUSTOMER_SIGNUP_EVENTS_TOPIC)
                .partitions(3)
                .replicas(2) //1 leader, 2 followers
                .configs(Map.of("min.insync.replicas", "1")) //2 servers is synchronized
                .compact()
                .build();
    }

    @Bean
    NewTopic createShoppingCartTopic() {
//        String shoppingCart = "customer-shopping-cart-events-topic";
        return TopicBuilder.name(CoreConstants.ACCOUNT_ORDER_EVENTS_TOPIC)
                .partitions(3)
                .replicas(2) //1 leader, 1 followers
                .configs(Map.of("min.insync.replicas", "1")) //2 servers is synchronized
                .compact()
                .build();
    }
}
