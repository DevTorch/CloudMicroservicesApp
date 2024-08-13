package springcloudms.inventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import springcloudms.inventoryservice.events.AddNewElectronicsEvent;
import springcloudms.inventoryservice.exception.KafkaSenderException;
import springcloudms.inventoryservice.model.dto.ElectronicsResponseDTO;
import springcloudms.inventoryservice.service.ElectronicsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElectronicsServiceImpl implements ElectronicsService {

//    private final KafkaTemplate<String, AddNewElectronicsEvent> kafkaTemplate;

    @Override
    public void saveAll(List<ElectronicsResponseDTO> electronics) {

    }

    @Override
    public Optional<ElectronicsResponseDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ElectronicsResponseDTO save(ElectronicsResponseDTO responseDTO) {

            var electronicsEvent = AddNewElectronicsEvent
                    .builder()
                    .articleNo(responseDTO.articleNo())
                    .warehouse(responseDTO.warehouse())
                    .productType(responseDTO.productType())
                    .title(responseDTO.title())
                    .quantity(responseDTO.quantity())
                    .lastStockUpdate(responseDTO.lastStockUpdate())
                    .purchasePrice(responseDTO.purchasePrice())
                    .category(responseDTO.category())
                    .model(responseDTO.model())
                    .characteristics(responseDTO.characteristics())
                    .description(responseDTO.description())
                    .build();

//            final CompletableFuture<SendResult<String, AddNewElectronicsEvent>> send =
//                    kafkaTemplate.send("product-created-events-topic", electronicsEvent.articleNo(), electronicsEvent);
//
//            send.whenComplete(
//                    (result, ex) -> {
//                        if (ex == null) {
//                            log.info("Book events sent successfully {}: ", result.getRecordMetadata());
//
//                            log.info("Topic: {}", result.getRecordMetadata().topic());
//                            log.info("Partition: {}", result.getRecordMetadata().partition());
//                            log.info("Offset: {}", result.getRecordMetadata().offset());
//                        } else {
//                            log.error("Book events failed to send: ", ex);
//                            throw new KafkaSenderException(LocalDateTime.now(), ex.getMessage());
//                        }
//                    });
            return responseDTO;
    }

    @Override
    public ElectronicsResponseDTO update(ElectronicsResponseDTO entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
