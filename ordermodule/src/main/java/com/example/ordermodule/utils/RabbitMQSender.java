package com.example.ordermodule.utils;

import com.example.ordermodule.config.RabbitMQConfig;
import com.example.ordermodule.dto.EmployeeDTO;
import com.example.ordermodule.dto.WarehouseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class RabbitMQSender {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<EmployeeDTO> sendToGetListEmployee(String messageDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Message newMessage = MessageBuilder.withBody(messageDTO.getBytes()).build();
        logger.info("Send Message from OrderModule to UserModule: " + messageDTO.toString());
        String result = (String) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.exchange, RabbitMQConfig.queue_user_module, newMessage);
        logger.info("Result from UserModule: {}", result);
        EmployeeDTO[] employeeDTOS = objectMapper.readValue(result, EmployeeDTO[].class);
        return Arrays.asList(employeeDTOS);
    }

    public List<WarehouseDTO> sendToGetListWarehouse() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Message newMessage = new Message("".getBytes(StandardCharsets.UTF_8));
        logger.info("Send Message from OrderModule to CommonModule");
        String result = (String) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.exchange, RabbitMQConfig.queue_common_module, newMessage);
        logger.info("Result from CommonModule: {}", result);
        WarehouseDTO[] warehouseDTOS = objectMapper.readValue(result, WarehouseDTO[].class);
        return Arrays.asList(warehouseDTOS);
    }
}
