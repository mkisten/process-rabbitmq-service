package ru.ertelecom.rebbitmqservice.controller.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ertelecom.rabbitmqservice.model.Action;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;
import ru.ertelecom.rabbitmqservice.service.DataParserService;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class DataParserServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DataParserService service;

    @Test
    public void testParseData_ValidJson() throws Exception {
        String jsonData = "{\"id\":1, \"action\":{\"action\":\"Test\"}, \"incident\":{\"incidentId\":123}}";
        RabbitMQMessage mockedMessage = new RabbitMQMessage();
        Action action = new Action();
        mockedMessage.setAction(action);  // Создаём и устанавливаем action

        // Настройка Mockito для возврата объекта mockedMessage, который уже содержит action.
        when(objectMapper.readValue(anyString(), eq(RabbitMQMessage.class))).thenReturn(mockedMessage);

        service.parseData(jsonData, mockedMessage);

        assertNotNull(mockedMessage.getAction());
        verify(objectMapper, times(1)).readValue(jsonData, RabbitMQMessage.class);
    }

    @Test
    public void testParseEmptyData() {
        RabbitMQMessage message = new RabbitMQMessage();
        String jsonData = "";
        assertThrows(DataParserService.DataParsingException.class, () -> service.parseData(jsonData, message));
    }

    @Test(expected = DataParserService.DataParsingException.class)
    public void testParseData_InvalidJson() throws Exception {
        String jsonData = "invalid json";
        RabbitMQMessage message = new RabbitMQMessage();

        // Настройка ObjectMapper для выброса JsonProcessingException при попытке разобрать невалидный JSON
        when(objectMapper.readValue(jsonData, RabbitMQMessage.class)).thenThrow(new JsonProcessingException("Parsing error") {});

        // Вызов метода, который должен обработать исключение
        service.parseData(jsonData, message);

        // Mockito.verify() для проверки, что метод readValue() был вызван с ожидаемыми параметрами
        verify(objectMapper).readValue(jsonData, RabbitMQMessage.class);
    }
}
