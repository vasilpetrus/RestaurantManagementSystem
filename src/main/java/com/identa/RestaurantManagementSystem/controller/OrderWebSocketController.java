package com.identa.RestaurantManagementSystem.controller;

import com.identa.RestaurantManagementSystem.entity.CustomerOrder;
import com.identa.RestaurantManagementSystem.dto.OrderForm;
import com.identa.RestaurantManagementSystem.service.CustomerOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * This class serves as a WebSocket controller for handling real-time communication related to orders.
 * It enables communication between the client and server using WebSocket, allowing orders and order status
 * updates to be sent and received in real-time.
 */
@Controller
@CrossOrigin
public class OrderWebSocketController {

    private final Logger log = LoggerFactory.getLogger(OrderWebSocketController.class);

    @Autowired
    CustomerOrderService orderService;

    /**
     * Handles WebSocket messages for making a new order.
     *
     * @param orderForm The WebSocket message containing order details in the OrderForm format.
     * @return A CustomerOrder object representing the newly created order.
     */
    @MessageMapping("order/add")
    @SendTo("/topic/order")
    public CustomerOrder makeOrder(OrderForm orderForm) {
        log.info("Requesting new order: {}", orderForm);

        return orderService.createNewOrder(
                orderForm.getItems(),
                orderForm.getCustomerName(),
                orderForm.getCustomerPhoneNumber(),
                orderForm.getDeliveryAddress(),
                orderForm.getObservation()
        );
    }

    /**
     * Handles WebSocket messages for notifying subscribers about order status updates.
     *
     * @return A message indicating that the notification has been sent ("done").
     */
    @MessageMapping("order/notify")
    @SendTo("/topic/order")
    public String notifyOrderStatusChanged() {
        log.info("Notifying all subscribers about order status update");

        return "done";
    }
}
