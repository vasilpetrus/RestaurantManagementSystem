package com.identa.RestaurantManagementSystem.service.implementation;

import com.identa.RestaurantManagementSystem.entity.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * This service class is responsible for sending WebSocket notifications related to new customer orders.
 * It utilizes the SimpMessagingTemplate to send messages to WebSocket subscribers.
 */
@Service
public class OrderWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Constructor for creating an instance of OrderWebSocketService.
     *
     * @param messagingTemplate The SimpMessagingTemplate used to send WebSocket messages.
     */
    @Autowired
    public OrderWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Sends a WebSocket notification about a new customer order to subscribers.
     *
     * @param order The CustomerOrder object representing the newly created order.
     */
    public void sendNewOrderNotification(CustomerOrder order) {
        messagingTemplate.convertAndSend("/topic/order", order);
    }
}