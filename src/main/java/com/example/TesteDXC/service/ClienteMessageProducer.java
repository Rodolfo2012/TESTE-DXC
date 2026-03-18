package com.example.TesteDXC.service;

import com.example.TesteDXC.config.RabbitMQConfig;
import com.example.TesteDXC.model.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(Cliente cliente) {
        try {
            String message = objectMapper.writeValueAsString(cliente);
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message);
            System.out.println(" [x] Sent '" + message + "'");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
