package ru.ertelecom.rabbitmqservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;

@Service
@RequiredArgsConstructor
public class DataParserService {

    private static final Logger logger = LoggerFactory.getLogger(DataParserService.class);
    private final ObjectMapper objectMapper;

    public void parseData(String jsonData, RabbitMQMessage message) throws DataParsingException {
        try {
            if (jsonData == null || jsonData.isEmpty()) {
                logger.error("Received invalid data for parsing.");
                throw new DataParsingException("Empty or null JSON data provided.");
            }

            RabbitMQMessage data = objectMapper.readValue(jsonData, RabbitMQMessage.class);
            message.setId(data.getId());
            message.setAction(data.getAction());
            message.setIncident(data.getIncident());
            message.setFilterIds(data.getFilterIds());
        } catch (Exception e) {
            logger.error("Data parsing error: {}", e.getMessage());
            throw new DataParsingException("Error parsing data: " + e.getMessage(), e);
        }
    }

    public static class DataParsingException extends Exception {
        public DataParsingException(String message) {
            super(message);
        }

        public DataParsingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
