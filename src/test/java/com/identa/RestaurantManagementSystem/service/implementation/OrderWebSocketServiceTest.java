package com.identa.RestaurantManagementSystem.service.implementation;

import com.identa.RestaurantManagementSystem.entity.CustomerOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.verify;

public class OrderWebSocketServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private OrderWebSocketService orderWebSocketService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNewOrderNotification_NotificationSent() {
        // Arrange
        CustomerOrder order = new CustomerOrder();

        // Act
        orderWebSocketService.sendNewOrderNotification(order);

        // Assert
        verify(messagingTemplate).convertAndSend("/topic/order", order);
    }
}