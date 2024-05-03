package ru.ertelecom.rabbitmqservice.service;

import org.springframework.stereotype.Service;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;
import ru.ertelecom.rabbitmqservice.repository.RabbitMQRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataProcessingService {

    private final RabbitMQRepository rabbitMQRepository;

    private final DataParserService dataParserService;

    public DataProcessingService(RabbitMQRepository rabbitMQRepository, DataParserService dataParserService) {
        this.rabbitMQRepository = rabbitMQRepository;
        this.dataParserService = dataParserService;
    }

    public void processData() {
        List<RabbitMQMessage> messages = rabbitMQRepository.findByStatus("NEW");
        int batchSize = 50;
        for (int i = 0; i < messages.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, messages.size());
            List<RabbitMQMessage> batch = messages.subList(i, endIndex);

            for (RabbitMQMessage message : batch) {
                String jsonData = message.getJsonData(); // Получаем JSON-строку из объекта message
                dataParserService.parseData(jsonData, message); // Передаем JSON-строку и объект message в метод parseData()
                processMessage(message); // обработка сообщения
            }

            // Сохранение батча сообщений
            rabbitMQRepository.saveAll(batch);

            // Можно добавить логирование или другую логику
        }
    }

    private void processMessage(RabbitMQMessage message) {
        // Обновление статуса сообщения на "PARSED" и установка даты обработки
        message.setStatus("PARSED");
        LocalDateTime currentTime = LocalDateTime.now();
        message.setParsedDt(currentTime);   // задается текущая дата и время

        // Сохранение обновленного сообщения в базу данных
        rabbitMQRepository.save(message);
    }
}

