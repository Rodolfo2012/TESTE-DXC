package com.example.TesteDXC.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.TesteDXC.config.RabbitMQConfig;
import com.example.TesteDXC.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteMessageProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ClienteMessageProducer clienteMessageProducer;

    @Test
    public void deveEnviarMensagem() throws JsonProcessingException {
        Cliente cliente = new Cliente(1L, "João", "joao@email.com", "123.456.789-00");
        String jsonMessage = "{\"id\":1,\"nome\":\"João\",\"email\":\"joao@email.com\",\"cpf\":\"123.456.789-00\"}";

        when(objectMapper.writeValueAsString(cliente)).thenReturn(jsonMessage);

        clienteMessageProducer.sendMessage(cliente);

        verify(rabbitTemplate).convertAndSend(eq(RabbitMQConfig.QUEUE_NAME), eq(jsonMessage));
    }
}
