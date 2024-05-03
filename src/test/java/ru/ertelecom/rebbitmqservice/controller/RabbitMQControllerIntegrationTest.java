package ru.ertelecom.rebbitmqservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.ertelecom.rabbitmqservice.RabbitMQServiceApplication;
import ru.ertelecom.rabbitmqservice.conroller.RabbitMQController;
import ru.ertelecom.rabbitmqservice.service.DataProcessingService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RabbitMQController.class)
@ContextConfiguration(classes = {RabbitMQServiceApplication.class})
public class RabbitMQControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataProcessingService dataProcessingService;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void processData_WhenCalled_ShouldInvokeServiceAndReturnSuccess() throws Exception {
        // Arrange
        doNothing().when(dataProcessingService).processData();

        // Act & Assert
        mockMvc.perform(post("/api/process-data")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Data processing initiated"));

        verify(dataProcessingService, times(1)).processData();
    }

    @Test
    void processData_WhenServiceThrows_ShouldReturnServerError() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Internal server error")).when(dataProcessingService).processData();

        // Act & Assert
        mockMvc.perform(post("/api/process-data")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(dataProcessingService, times(1)).processData();
    }
}
