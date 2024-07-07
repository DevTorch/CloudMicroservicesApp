package springcloudms.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import springcloudms.inventoryservice.model.dto.ProductRequestDTO;
import springcloudms.inventoryservice.model.dto.ProductResponseDTO;
import springcloudms.inventoryservice.service.InventoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public final class InventoryInnerController {

    private final InventoryService inventoryService;

    @GetMapping("/product/instock")
    public Boolean isInStock(@RequestBody ProductRequestDTO productRequestDTO) {
        return inventoryService.isInStock(productRequestDTO.articleNo(), productRequestDTO.quantity());
    }

    @GetMapping("/product/{articleNo}")
    @Tag(name = "Product API", description = "Api для работы Product Base Data.")
    @Operation(summary = "Получение ProductResponseDTO по articleNo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о продукте найдена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Информация о продукте не найдена",
                    content = @Content)
    })
    public ResponseEntity<ProductResponseDTO> findProductDTOById(@PathVariable("articleNo") String articleNo) {

        ProductResponseDTO productDTO = inventoryService.findProductDTOByArticleNo(articleNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/product/all")
    @Tag(name = "Product API", description =  "Api для работы Product Base Data.")
    @Operation(summary = "Получение списка всех товаров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список все товаров",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductRequestDTO.class))}),
            @ApiResponse(responseCode = "500", description = "API Request Filed",
                    content = @Content)
    })
    public List<ProductResponseDTO> findAll() {
        return inventoryService.findAll();
    }
}
