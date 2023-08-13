package com.identa.RestaurantManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This class serves as a controller for handling requests related to viewing customer orders.
 * It is responsible for rendering the "orders" page to display a list of orders.
 */
@Controller
public class OrdersController {

    @GetMapping("/orders")
    public String showOrders() {
        return "orders";
    }
}

