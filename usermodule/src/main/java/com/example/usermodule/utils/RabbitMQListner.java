package com.example.usermodule.utils;

import com.example.usermodule.config.RabbitMQConfig;
import com.example.usermodule.entity.Employee;
import com.example.usermodule.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class RabbitMQListner {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQListner.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.queue_user_module)
    public String receivedMessage(Message message) throws IOException {
        logger.info("Receive Message from OrderModule");
        ObjectMapper objectMapper = new ObjectMapper();
        String role = new String(message.getBody());
        List<Employee> employees = employeeRepository.findAllByRole(role);
        String res = objectMapper.writeValueAsString(employees);
        logger.info("Response from UserModule: {}", res);
        return res;
    }
}
