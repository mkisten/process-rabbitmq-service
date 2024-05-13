package ru.ertelecom.rabbitmqservice.service;

import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;

public interface IDataParserService {
    void parseData(String jsonData, RabbitMQMessage message) throws DataParserService.DataParsingException;
    void validateJsonData(String jsonData) throws DataParserService.DataParsingException;
    void populateRabbitMQMessage(String jsonData, RabbitMQMessage message) throws DataParserService.DataParsingException;
}
