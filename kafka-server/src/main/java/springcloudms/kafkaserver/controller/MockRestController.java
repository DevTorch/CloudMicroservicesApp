package springcloudms.kafkaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/response")
public class MockRestController {

    @GetMapping("/200")
    public ResponseEntity<String> return200ok() {
        return ResponseEntity.ok().body("200");
    }

    @GetMapping("/500")
    public ResponseEntity<String> return500internalServerError() {
        return ResponseEntity.internalServerError().body("500");
    }
}
