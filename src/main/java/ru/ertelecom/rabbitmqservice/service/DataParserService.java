package ru.ertelecom.rabbitmqservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;
import org.springframework.stereotype.Service;

@Service
public class DataParserService {

    private final ObjectMapper objectMapper;

    @Autowired
    public DataParserService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private static final Logger logger = LoggerFactory.getLogger(DataParserService.class);

    public void parseData(String jsonData, RabbitMQMessage message) {
        try {
            if (jsonData == null || jsonData.isEmpty()) {
                logger.error("Получены некорректные данные для парсинга.");
                return; // или выбросить исключение
            }

            // Парсим JSON данные в объект
            RabbitMQMessage data = objectMapper.readValue(jsonData, RabbitMQMessage.class);

            // Устанавливаем значения всех полей объекта RabbitMQMessage на основе данных из RabbitMQMessageData
            message.setId(data.getId());
            message.setAction(data.getAction());
            message.setIncident(data.getIncident());
            message.setFilterIds(data.getFilterIds());

            // Дополнительная логика по обработке данных, если необходимо
        } catch (Exception e) {
            logger.error("Ошибка парсинга данных: {}", e.getMessage());
            // Дополнительная логика обработки ошибок
        }
    }

}

