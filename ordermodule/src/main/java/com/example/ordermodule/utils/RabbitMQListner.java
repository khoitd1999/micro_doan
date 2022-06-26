package com.example.ordermodule.utils;

import com.example.ordermodule.config.RabbitMQConfig;
import com.example.ordermodule.enity.WareHouseReceipt;
import com.example.ordermodule.repository.WarehouseReceiptRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RabbitMQListner {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQListner.class);

    @Autowired
    private WarehouseReceiptRepository warehouseReceiptRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.queue_order_module)
    public String receivedMessage(Message message) throws IOException {
        logger.info("Receive Message from CommonModule");
        ObjectMapper objectMapper = new ObjectMapper();
        String reqJson = new String(message.getBody());
        List<List<Long>> ids = objectMapper.readValue(reqJson, List.class);
        List<WareHouseReceipt> wareHouseReceipts = warehouseReceiptRepository.loadWarehouseReceipt(ids.get(0), ids.get(1));
        String res = objectMapper.writeValueAsString(wareHouseReceipts);
        logger.info("Response from OrderModule: {}", res);
        return res;
    }
}
