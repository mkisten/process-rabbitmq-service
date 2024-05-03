package ru.ertelecom.rabbitmqservice.conroller;

import ru.ertelecom.rabbitmqservice.service.DataProcessingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RabbitMQController {

    private final DataProcessingService dataProcessingService;

    public RabbitMQController(DataProcessingService dataProcessingService) {
        this.dataProcessingService = dataProcessingService;
    }

    @PostMapping("/process-data")
    public ResponseEntity<String> processData() {
        try {
            dataProcessingService.processData();
            return ResponseEntity.ok("Data processing initiated");
        } catch (Exception e) {
            // Логгирование ошибки может быть добавлено здесь для диагностики
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
