package com.example.commonmodule.utils;

import com.example.commonmodule.config.RabbitMQConfig;
import com.example.commonmodule.dto.WarehouseDTO;
import com.example.commonmodule.dto.WarehouseReceiptDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RabbitMQSender {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<WarehouseReceiptDTO> sendToGetListWarehouse(List<Long> idWarehouse, List<Long> idPro) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<List<Long>> ids = new ArrayList<>();
        ids.add(idWarehouse);
        ids.add(idPro);
        Message newMessage = MessageBuilder.withBody(objectMapper.writeValueAsBytes(ids)).build();
        logger.info("Send Message from CommonModule to OrderModule");
        String result = (String) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.exchange, RabbitMQConfig.queue_order_module, newMessage);
        logger.info("Result from OrderModule: {}", result);
        WarehouseReceiptDTO[] warehouseReceiptDTOS = objectMapper.readValue(result, WarehouseReceiptDTO[].class);
        return Arrays.asList(warehouseReceiptDTOS);
    }
}
