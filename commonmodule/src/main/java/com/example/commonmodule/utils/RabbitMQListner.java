package com.example.commonmodule.utils;

import com.example.commonmodule.config.RabbitMQConfig;
import com.example.commonmodule.entity.WareHouse;
import com.example.commonmodule.repository.WareHouseRepository;
import com.example.commonmodule.service.WareHouseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class RabbitMQListner {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQListner.class);

    @Autowired
    private WareHouseRepository wareHouseRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.queue_common_module)
    public String receivedMessage(Message message) throws IOException {
        logger.info("Receive Message from OrderModule");
        List<WareHouse> wareHouses = wareHouseRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(wareHouses);
        logger.info("Response from CommonModule: {}", res);
        return res;
    }
}
