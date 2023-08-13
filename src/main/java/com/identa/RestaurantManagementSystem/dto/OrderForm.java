package com.identa.RestaurantManagementSystem.dto;

import com.identa.RestaurantManagementSystem.entity.Item;
import lombok.Data;

import java.util.List;

/**
 * This class serves as a data transfer object (DTO) representing an order form for creating customer orders.
 * It encapsulates the necessary information required to place an order, including items, customer details,
 * delivery address, and additional observations.
 */
@Data
public class OrderForm {

    private List<Item> items;
    private String customerName;
    private String customerPhoneNumber;
    private String deliveryAddress;
    private String observation;

    public OrderForm() {
    }

    public OrderForm(List<Item> items, String customerName, String customerPhoneNumber, String deliveryAddress, String observation) {
        this.items = items;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.deliveryAddress = deliveryAddress;
        this.observation = observation;
    }
}
