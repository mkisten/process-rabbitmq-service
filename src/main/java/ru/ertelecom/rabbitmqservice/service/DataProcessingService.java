package ru.ertelecom.rabbitmqservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;
import ru.ertelecom.rabbitmqservice.repository.RabbitMQRepository;
import ru.ertelecom.rabbitmqservice.service.DataParserService.DataParsingException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataProcessingService {

    private final RabbitMQRepository rabbitMQRepository;
    private final DataParserService dataParserService;

    public void processData() {
        List<RabbitMQMessage> messages = rabbitMQRepository.findByStatus("NEW");

        messages.forEach(message -> {
            String jsonData = message.getJsonData();
            try {
                dataParserService.parseData(jsonData, message);
                processMessage(message);
            } catch (DataParsingException e) {
                message.setStatus("ERROR");
                message.setParsedDt(LocalDateTime.now());
                // Optional: Add logging here
            }
        });

        rabbitMQRepository.saveAll(messages);
    }

    private void processMessage(RabbitMQMessage message) {
        message.setStatus("PARSED");
        message.setParsedDt(LocalDateTime.now());
        // Optional: Additional processing logic
    }
}
