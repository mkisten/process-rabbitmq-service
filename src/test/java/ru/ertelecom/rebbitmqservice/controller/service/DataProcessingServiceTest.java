package ru.ertelecom.rebbitmqservice.controller.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;
import ru.ertelecom.rabbitmqservice.repository.RabbitMQRepository;
import ru.ertelecom.rabbitmqservice.service.DataParserService;
import ru.ertelecom.rabbitmqservice.service.DataProcessingService;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DataProcessingServiceTest {

    @Mock
    private RabbitMQRepository rabbitMQRepository;

    @Mock
    private DataParserService dataParserService;

    @InjectMocks
    private DataProcessingService dataProcessingService;

    @Test
    void processData_WithValidData_ShouldProcessSuccessfully() throws Exception {
        // Given
        RabbitMQMessage message = RabbitMQMessage.builder()
                .jsonData("valid data")
                .status("NEW")
                .build();
        List<RabbitMQMessage> messages = Arrays.asList(message);
        when(rabbitMQRepository.findByStatus("NEW")).thenReturn(messages);

        // When
        dataProcessingService.processData();

        // Then
        verify(dataParserService, times(1)).parseData(anyString(), any(RabbitMQMessage.class));
        verify(rabbitMQRepository, times(1)).saveAll(messages);
    }

    @Test
    void processData_WithErrorInParsing_ShouldHandleError() throws Exception {
        // Given
        RabbitMQMessage message = RabbitMQMessage.builder()
                .jsonData("invalid data")
                .status("NEW")
                .build();
        List<RabbitMQMessage> messages = Arrays.asList(message);
        when(rabbitMQRepository.findByStatus("NEW")).thenReturn(messages);
        doThrow(new DataParserService.DataParsingException("Parsing error"))
                .when(dataParserService).parseData(anyString(), any(RabbitMQMessage.class));

        // When
        dataProcessingService.processData();

        // Then
        verify(dataParserService, times(1)).parseData(anyString(), any(RabbitMQMessage.class));
        assertEquals("ERROR", message.getStatus());
        verify(rabbitMQRepository, times(1)).saveAll(messages);
    }
}
