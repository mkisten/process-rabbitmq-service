package ru.ertelecom.rebbitmqservice.controller.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ertelecom.rabbitmqservice.model.Action;
import ru.ertelecom.rabbitmqservice.model.Incident;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;
import ru.ertelecom.rabbitmqservice.service.XMLGenerationService;

import static org.junit.jupiter.api.Assertions.*;

class XMLGenerationServiceTest {

    private XMLGenerationService xmlGenerationService;

    @BeforeEach
    void setUp() {
        xmlGenerationService = new XMLGenerationService();
    }

    @Test
    void generateXML_withValidMessage_shouldGenerateExpectedXML() {
        // Setup
        RabbitMQMessage message = new RabbitMQMessage();
        message.setId(1L);
        Action action = new Action();
        action.setAction("Create");
        message.setAction(action);
        Incident incident = new Incident();
        incident.setIncidentId(101);
        message.setIncident(incident);

        // Act
        String xmlResult = xmlGenerationService.generateXML(message);

        // Assert
        assertNotNull(xmlResult);
        assertTrue(xmlResult.contains("<RabbitMQMessage>"));
        assertTrue(xmlResult.contains("<id>1</id>"));
        assertTrue(xmlResult.contains("<action>Create</action>"));
        assertTrue(xmlResult.contains("<incident>"));
        assertTrue(xmlResult.contains("<incidentId>101</incidentId>"));
    }

    @Test
    void generateXML_withNullMessage_shouldThrowIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> xmlGenerationService.generateXML(null));
    }

    @Test
    void generateXML_withMessageHavingNullFields_shouldHandleNullsGracefully() {
        // Setup
        RabbitMQMessage message = new RabbitMQMessage();
        message.setId(2L);
        message.setAction(null); // No action
        message.setIncident(null); // No incident

        // Act
        String xmlResult = xmlGenerationService.generateXML(message);

        // Assert
        assertNotNull(xmlResult);
        assertTrue(xmlResult.contains("<RabbitMQMessage>"));
        assertTrue(xmlResult.contains("<id>2</id>"));
        assertFalse(xmlResult.contains("<action>"));
        assertFalse(xmlResult.contains("<incident>"));
    }
}