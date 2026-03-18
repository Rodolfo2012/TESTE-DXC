package com.example.TesteDXC.service;

import com.example.TesteDXC.config.RabbitMQConfig;
import com.example.TesteDXC.model.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteMessageConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println(" [x] Received '" + message + "'");
        try {
            Cliente cliente = objectMapper.readValue(message, Cliente.class);
            System.out.println("Processando cliente: " + cliente.getNome());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
