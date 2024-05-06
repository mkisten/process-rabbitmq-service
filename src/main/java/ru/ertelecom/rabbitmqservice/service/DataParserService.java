package ru.ertelecom.rabbitmqservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataParserService {

    private final ObjectMapper objectMapper;

    public void parseData(String jsonData, RabbitMQMessage message) throws DataParsingException {
        try {
            validateJsonData(jsonData);
            populateRabbitMQMessage(jsonData, message);
        } catch (Exception e) {
            handleParsingException(e);
        }
    }

    private void validateJsonData(String jsonData) throws DataParsingException {
        if (StringUtils.isEmpty(jsonData)) {
            String errorMessage = "Предоставлены пустые или нулевые JSON данные.";
            log.error("Получены недопустимые данные для парсинга: {}", errorMessage);
            throw new DataParsingException(errorMessage);
        } else {
            log.debug("JSON данные успешно прошли проверку.");
        }
    }

    private void populateRabbitMQMessage(String jsonData, RabbitMQMessage message) throws DataParsingException {
        try {
            RabbitMQMessage data = objectMapper.readValue(jsonData, RabbitMQMessage.class);
            message.setId(data.getId());
            message.setAction(data.getAction());
            message.setIncident(data.getIncident());
            message.setFilterIds(data.getFilterIds());
            log.info("RabbitMQMessage успешно заполнен.");
        } catch (IOException e) {
            throw new DataParsingException("Ошибка парсинга данных: " + e.getMessage(), e);
        }
    }

    private void handleParsingException(Exception e) throws DataParsingException {
        log.error("Ошибка парсинга данных: {}", e.getMessage(), e);
        throw new DataParsingException("Ошибка парсинга данных " + e.getMessage(), e);
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
