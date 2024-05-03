package ru.ertelecom.rebbitmqservice.controller.service;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ertelecom.rabbitmqservice.model.RabbitMQMessage;
import ru.ertelecom.rabbitmqservice.repository.RabbitMQRepository;
import ru.ertelecom.rabbitmqservice.service.DataProcessingService;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class DataProcessingServiceTest {

    @Mock
    private RabbitMQRepository rabbitMQRepository;

    @InjectMocks
    private DataProcessingService dataProcessingService;

    @Test
    public void testProcessData() {
        // Подготовка данных
        List<RabbitMQMessage> messages = new ArrayList<>();
        // добавление необходимых данных в messages

        // Настройка поведения репозитория
        when(rabbitMQRepository.findByStatus("NEW")).thenReturn(messages);

        // Вызов метода для тестирования
        dataProcessingService.processData();

        // Проверка, что метод сохранения был вызван правильное количество раз
        verify(rabbitMQRepository, times(messages.size())).save(any());
    }
}
