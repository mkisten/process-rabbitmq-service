package ru.ertelecom.rabbitmqservice.service;

import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;

public interface IDataProcessingService {
    void processData();
    void processMessage(RabbitMQMessage message);
}
