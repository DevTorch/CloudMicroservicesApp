package springcloudms.authservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AccountKafkaProducerConfig {

    @Value("${spring.kafka.producer.properties.linger.ms}")
    private String linger;

    @Bean
    public ProducerFactory<String, Object> kafkaProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfigs());
    }

    private final Environment environment;

    Map<String, Object> kafkaProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.producer.key-serializer"));
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, environment.getProperty("spring.kafka.producer.value-serializer"));
        props.put(ProducerConfig.ACKS_CONFIG, environment.getProperty("spring.kafka.producer.acks"));
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.delivery.timeout.ms"));
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.request.timeout.ms"));
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, environment.getProperty("spring.kafka.producer.max.in.flight.requests.per.connection"));
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); //Default
        return props;
    }

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfigs());
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    NewTopic createSignUpTopic() {
        String signUpTopic = "customer-signup-event-topic";
        return TopicBuilder.name(signUpTopic)
                .partitions(3)
                .replicas(3) //1 leader, 2 followers
                .configs(Map.of("min.insync.replicas", "2")) //2 servers is synchronized
                .compact()
                .build();
    }

    @Bean
    NewTopic createShoppingCartTopic() {
        String shoppingCart = "customer-shopping-cart-event-topic";
        return TopicBuilder.name(shoppingCart)
                .partitions(3)
                .replicas(3) //1 leader, 2 followers
                .configs(Map.of("min.insync.replicas", "2")) //2 servers is synchronized
                .compact()
                .build();
    }
}
