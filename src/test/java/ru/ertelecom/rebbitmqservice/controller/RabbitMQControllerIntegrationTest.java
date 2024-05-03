package ru.ertelecom.rebbitmqservice.controller;

import ru.ertelecom.rabbitmqservice.service.DataProcessingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class RabbitMQControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataProcessingService dataProcessingService;

    @Test
    void testProcessDataEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/process-data")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Проверяем, что метод processData() вызван один раз
        verify(dataProcessingService, times(1)).processData();
    }
}

