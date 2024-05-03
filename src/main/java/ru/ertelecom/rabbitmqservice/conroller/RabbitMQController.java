package ru.ertelecom.rabbitmqservice.conroller;

import ru.ertelecom.rabbitmqservice.service.DataProcessingService;
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
    public String processData() {
        // Здесь можно вызвать метод для обработки данных
        dataProcessingService.processData();
        return "Data processing initiated";
    }
}
