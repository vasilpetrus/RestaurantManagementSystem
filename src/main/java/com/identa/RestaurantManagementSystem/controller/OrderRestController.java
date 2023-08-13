package com.identa.RestaurantManagementSystem.controller;

import com.identa.RestaurantManagementSystem.entity.CustomerOrder;
import com.identa.RestaurantManagementSystem.dto.OrderForm;
import com.identa.RestaurantManagementSystem.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class serves as a RESTful controller for managing customer orders through API endpoints.
 * It handles HTTP requests related to retrieving, creating, updating, and deleting orders.
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderRestController {

    @Autowired
    CustomerOrderService customerOrderService;

    /**
     * Handles the HTTP GET request for retrieving all customer orders.
     *
     * @return A list of CustomerOrder objects representing all existing orders.
     */
    @GetMapping
    @CrossOrigin
    public List<CustomerOrder> getAll() {
        return customerOrderService.getCustomerOrders();
    }

    /**
     * Handles the HTTP POST request for creating a new customer order.
     *
     * @param form The JSON request body containing order details in the OrderForm format.
     * @return A CustomerOrder object representing the newly created order.
     */
    @PostMapping
    @CrossOrigin
    public CustomerOrder createNewOrder(@RequestBody OrderForm form) {
        return customerOrderService.createNewOrder(
                form.getCustomerName(),
                form.getCustomerPhoneNumber(),
                form.getDeliveryAddress(),
                form.getItems(),
                form.getObservation(),
                form.getTotalAmount()
        );
    }

    /**
     * Handles the HTTP PUT request for updating the status of a customer order.
     *
     * @param orderId The ID of the order to be updated.
     * @param status  The new status to set for the order.
     */
    @PutMapping("/{orderId}")
    @CrossOrigin
    public void updateState(@PathVariable long orderId, @RequestParam String status) {
        customerOrderService.updateStatus(orderId, status);
    }

    /**
     * Handles the HTTP DELETE request for deleting a customer order.
     *
     * @param orderId The ID of the order to be deleted.
     */
    @DeleteMapping("/{orderId}")
    @CrossOrigin
    public void deleteOrder(@PathVariable long orderId) {
        customerOrderService.deleteOrderById(orderId);
    }
}