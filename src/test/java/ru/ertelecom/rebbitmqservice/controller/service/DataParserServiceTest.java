package ru.ertelecom.rebbitmqservice.controller.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ertelecom.rabbitmqservice.model.Action;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;
import ru.ertelecom.rabbitmqservice.repository.RabbitMQRepository;
import ru.ertelecom.rabbitmqservice.service.DataParserService;

@ExtendWith(MockitoExtension.class)
public class DataParserServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DataParserService dataParserService;


    @Test
    public void testParseData_ValidJsonData() throws JsonProcessingException {
        // Подготовка данных
        String jsonData = "{\"id\": 123, \"action\": {\"action\": \"test_action\"}}";
        RabbitMQMessage message = new RabbitMQMessage();

        // Настройка поведения objectMapper
        RabbitMQMessage parsedMessage = new RabbitMQMessage();
        Action action = new Action();
        action.setAction("test_action");
        parsedMessage.setAction(action);
        when(objectMapper.readValue(jsonData, RabbitMQMessage.class)).thenReturn(parsedMessage);

        // Вызов метода для тестирования
        dataParserService.parseData(jsonData, message);

        // Проверка, что поля message были установлены правильно
        assertEquals(parsedMessage.getAction().getAction(), message.getAction().getAction());
    }

    @Test
    public void testParseData_InvalidJsonData() {
        // Подготовка данных
        String jsonData = null;
        RabbitMQMessage message = new RabbitMQMessage();

        // Вызов метода для тестирования
        dataParserService.parseData(jsonData, message);

        // Проверка, что не произошло исключений и что данные message не изменились
        assertEquals(null, message.getAction());
    }

    // Дополнительные тесты можно добавить для обработки других сценариев и ошибок
}

